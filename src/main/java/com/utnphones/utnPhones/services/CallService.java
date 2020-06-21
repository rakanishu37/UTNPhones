package com.utnphones.utnPhones.services;

import com.utnphones.utnPhones.domain.Call;
import com.utnphones.utnPhones.domain.PhoneLine;
import com.utnphones.utnPhones.dto.CallDto;
import com.utnphones.utnPhones.dto.CallsDatesDTO;
import com.utnphones.utnPhones.dto.DestinyCallsCountDTO;
import com.utnphones.utnPhones.dto.TopTenDestinies;
import com.utnphones.utnPhones.exceptions.CallNotFoundException;
import com.utnphones.utnPhones.exceptions.PhoneLineNotFoundException;
import com.utnphones.utnPhones.projections.CallsDates;
import com.utnphones.utnPhones.projections.DestinyQuantity;
import com.utnphones.utnPhones.repository.CallRepository;
import com.utnphones.utnPhones.utils.DateFormatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.text.ParseException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CallService {
    private CallRepository callRepository;
    private PhoneLineService phoneLineService;
    @Autowired
    public CallService(CallRepository callRepository,PhoneLineService phoneLineService) {
        this.callRepository = callRepository;
        this.phoneLineService = phoneLineService;
    }


    public List<CallsDates> getAll(Integer quantity, Integer from, String dateFrom, String dateTo) throws ParseException {
        if(dateFrom==null || dateTo==null){
            return this.callRepository.findAll(quantity, from);
        }else{
            return this.callRepository.findAllByDates(from, quantity, DateFormatUtil.formatDate(dateFrom), DateFormatUtil.formatDate(dateTo));
        }
    }


    public URI create(CallDto callDto) throws PhoneLineNotFoundException {
        PhoneLine numberFrom = phoneLineService.getByPhoneNumber(callDto.getNumberFrom());
        PhoneLine numberTo = phoneLineService.getByPhoneNumber(callDto.getNumberTo());

        Call created = callRepository.save(Call.builder()
                        .phoneFrom(numberFrom)
                        .phoneTo(numberTo)
                        .duration(callDto.getDuration())
                        .date(callDto.getDate())
                        .build());
         return getLocation(created);
    }

    public Call getById(Integer idCall) throws CallNotFoundException {
        return callRepository.findById(idCall)
                .orElseThrow(() -> new CallNotFoundException());
    }

    public Map<String, List<CallsDates>> getCalls(Integer idClient, String from, String to) throws ParseException {
        List<CallsDates> calls = null;
        if(from == null || to == null){
            calls = callRepository.getAllCallByClient(idClient);
        } else{
            calls = callRepository.getAllByIdClientBetweenDates(idClient,DateFormatUtil.formatDate(from),DateFormatUtil.formatDate(to));
        }

        return calls.stream()
                .sorted(Comparator.comparing(CallsDates::getDate))
                .collect(Collectors.groupingBy(CallsDates::getPhoneNumberOrigin));
    }

    public TopTenDestinies getTopTenDestiniesByClient(Integer idClient){
        List<DestinyQuantity> list = callRepository.getTopTenDestiniesByClient(idClient);
        return TopTenDestinies.fromList(list);
    }

    private URI getLocation(Call call) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(call.getId())
                .toUri();
    }


}
