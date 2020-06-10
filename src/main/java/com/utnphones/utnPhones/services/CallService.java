package com.utnphones.utnPhones.services;

import com.utnphones.utnPhones.domain.Call;
import com.utnphones.utnPhones.domain.PhoneLine;
import com.utnphones.utnPhones.dto.CallDto;
import com.utnphones.utnPhones.exceptions.CallNotFoundException;
import com.utnphones.utnPhones.exceptions.PhoneLineNotFoundException;
import com.utnphones.utnPhones.projections.CallsDates;
import com.utnphones.utnPhones.projections.TopTenDestinies;
import com.utnphones.utnPhones.repository.CallRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
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


    public List<CallsDates> getAll(Integer to, Integer from, String dateFrom, String dateTo) throws ParseException {

        if(dateFrom==null || dateTo==null){
            return this.callRepository.findAll(to, from);
        }else{

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = simpleDateFormat.parse(dateFrom);
            Date date2 = simpleDateFormat.parse(dateTo);
            String fromDate = simpleDateFormat.format(date1);
            String toDate = simpleDateFormat.format(date2);
            return this.callRepository.findAllByDates(from, to, fromDate, toDate);
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


    public Map<String, List<CallsDates>> getAllByClient(Integer id) {
        List<CallsDates> callsDatesList= callRepository.getAllCallByClient(id);
        return callsDatesList.stream()
                .sorted(Comparator.comparing(CallsDates::getDate))
                .collect(Collectors.groupingBy(CallsDates::getPhoneNumberOrigin));
    }

    public Map<String, List<CallsDates>> getCallsBetweenDates(Integer idClient, Date from, Date to){
        List<CallsDates> calls = callRepository.getAllByIdClientBetweenDates(idClient,from,to);

        return calls.stream()
                .sorted(Comparator.comparing(CallsDates::getDate))
                .collect(Collectors.groupingBy(CallsDates::getPhoneNumberOrigin));
    }

    public List<TopTenDestinies> getTopTenDestiniesByClient(Integer idClient){
        return this.callRepository.getTopTenDestiniesByClient(idClient);
    }

    private URI getLocation(Call call) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(call.getId())
                .toUri();
    }


}
