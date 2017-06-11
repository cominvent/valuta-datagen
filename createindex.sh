#!/usr/bin/env bash
curl -XPUT 'localhost:9200/valuta?pretty' -H 'Content-Type: application/json' -d'
{
    "settings": {
        "number_of_shards": 3
    },
    "mappings": {
        "trans": {
            "_all": {
                "enabled": false
            },
            "dynamic": "true",
            "properties": {
                "behandlingskode": {
                    "type": "keyword"
                },
                "belopetgjelder": {
                    "type": "text"
                },
                "betalingsart": {
                    "type": "keyword"
                },
                "bic": {
                    "type": "keyword"
                },
                "detaljtranstype": {
                    "type": "keyword"
                },
                "innut": {
                    "type": "keyword"
                },
                "location": {
                    "type": "keyword"
                },
                "nokbelop": {
                    "type": "float"
                },
                "nokbelopstr": {
                    "type": "keyword"
                },
                "noradresse": {
                    "type": "text"
                },
                "noretternavn": {
                    "fields": {
                        "keyword": {
                            "ignore_above": 256,
                            "type": "keyword"
                        }
                    },
                    "type": "text"
                },
                "norfodselsdato": {
                    "type": "keyword"
                },
                "norfodselsnr": {
                    "type": "keyword"
                },
                "norfornavn": {
                    "type": "text"
                },
                "norkontonr": {
                    "type": "keyword"
                },
                "norpartstype": {
                    "type": "keyword"
                },
                "norpostnr": {
                    "type": "keyword"
                },
                "norpoststed": {
                    "type": "keyword"
                },
                "norstatsborgerland": {
                    "type": "keyword"
                },
                "phonnoretternavn": {
                    "type": "text"
                },
                "phonnorfornavn": {
                    "type": "text"
                },
                "phonutletternavn": {
                    "type": "text"
                },
                "phonutlfornavn": {
                    "type": "text"
                },
                "rapportbank": {
                    "type": "keyword"
                },
                "rapporteringspliktig": {
                    "type": "keyword"
                },
                "referanse": {
                    "type": "keyword"
                },
                "transdato": {
                    "type": "date"
                },
                "utladresse": {
                    "type": "text"
                },
                "utletternavn": {
                    "fields": {
                        "keyword": {
                            "ignore_above": 256,
                            "type": "keyword"
                        }
                    },
                    "type": "text"
                },
                "utlfodselsnr": {
                    "type": "keyword"
                },
                "utlfornavn": {
                    "type": "text"
                },
                "utlkontoland": {
                    "type": "keyword"
                },
                "utlkontonr": {
                    "type": "keyword"
                },
                "utlland": {
                    "type": "keyword"
                },
                "utlpostnr": {
                    "type": "keyword"
                },
                "utlpoststed": {
                    "type": "keyword"
                },
                "valutabelop": {
                    "type": "float"
                },
                "valutabelopstr": {
                    "type": "keyword"
                },
                "valutakode": {
                    "type": "keyword"
                },
                "vrtransid": {
                    "type": "keyword"
                }
            }
        }
    }
}
'
