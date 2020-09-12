BUCKET_DATA[
    {
        "key": "Client::11245788", // CLIENT
        "docType": "Client",
        "DNI":"11245788",
        "username": "my_username",
        "password": "my_password",
        "city":{
            "docType": "City",
            "city_name": "Mar del Plata",
            "prefix": "223",
            "province":{
                "docType": "Province",
                "province_name":"Buenos Aires"
            }
        },
        "phonelines": [
            {
                "docType":"PhoneLine",
                "line_number":"2235447148",
                "line_type":"mobile",
                "line_status":"active"
            },
            {
                "docType":"PhoneLine",
                "line_number":"4774558749",
                "line_type":"home",
                "line_status":"suspended"
            }
        ]
    },
    {
        "key":"Call::1",
        "phone_number_from":"21155422598",
        "phone_number_to":"0114588659",
        "call_date":"2020-20-06 17:45:02 +0000",
        "fare_per_minute":"15.85",
        "seconds_duration":"745",
        "total_price":"196.8041666666667",
        "invoice":{}
    },
    {
        "key":"Invoice::1",
        "calls":[
            {
                "phone_number_from":"21155422598",
                "phone_number_to":"0114588659",
                "call_date":"2020-20-06 17:45:02 +0000",
                "fare_per_minute":"15.85",
                "seconds_duration":"745",
                "total_price":"196.8041666666667"   
            },
            {
                "phone_number_from":"21155422598",
                "phone_number_to":"0114588659",
                "call_date":"2020-22-06 07:45:02 +0000",
                "fare_per_minute":"15.85",
                "seconds_duration":"745",
                "total_price":"196.8041666666667"
            }
        ],
        "number_of_calls":"2",
        "price_cost":"450",
        "total_price":"987.144",
        "invoice_date":"2020-23-06 18:31:00 +0000",
        "invoice_due_date":"2020-08-07 18:31:00 +0000"
    }
]