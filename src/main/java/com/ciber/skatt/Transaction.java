package com.ciber.skatt;

import com.ciber.skatt.sentence.SentenceBuilder;
import com.google.gson.Gson;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import static jdk.nashorn.internal.objects.NativeArray.reverse;

class Transaction {
    private static final Gson gson = new Gson();
    private Person p = null;
    private Organization o = null;
    private Foreign f;
    private float amountNo;
    private Instant transTime;
    private String innut;

    public Transaction(Person p, Foreign f, float amountNo, Instant transTime, String innut) {
        this.p = p;
        this.f = f;
        this.amountNo = amountNo;
        this.transTime = transTime;
        this.innut = innut;
    }

    public Transaction(Organization o, Foreign f, float amountNo, Instant transTime, String innut) {
        this.p = o;
        this.o = o;
        this.f = f;
        this.amountNo = amountNo;
        this.transTime = transTime;
        this.innut = innut;
    }

    public String toString() {
        Map<String, Object> map = new HashMap<>();

        map.put("transdato", LocalDateTime.ofInstant(transTime, ZoneId.of("UTC"))
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")));
        map.put("transtype", "BANK");
        map.put("detaljtranstype", "Overføring");
        map.put("nokbelop", amountNo);
        map.put("valutabelop", ExchangeRates.nokTo(amountNo, f.getCountry().getCurrencyCode()));
        map.put("valutakode", f.getCountry().getCurrencyCode());
        map.put("innut", innut);
        map.put("behandlingskode", "");
        map.put("norland", "NO");
        map.put("norkontonr", p.getKontoNr());
        map.put("norpartstype", o == null ? "P" : "F");
        if (o != null) {
            map.put("nororgnr", o.getOrgno());
            map.put("noretternavn", p.getName());
        } else {
            map.put("noretternavn", p.getLastName());
            map.put("norfornavn", p.getFirstName());
            map.put("norfodselsnr", p.getSsn());
            map.put("norfodselsdato", p.getBirthDate());
        }
        map.put("norpoststed", p.getCity());
        map.put("norpostnr", p.getPostcode());
        map.put("noradresse", p.getAdresse());
        map.put("utlfodselsnr", f.getSsn());
        map.put("rapporteringspliktig", f.getSsn());
        map.put("utlkontonr", f.getKontoNr());
        map.put("bic", f.getCountry().getCode() + RandomUtil.string(6));
        map.put("referanse", RandomUtil.string(15));
        map.put("rapportbank", String.format("%04d", RandomUtil.getNumber(9999)));
        map.put("betalingsart", "14");
        map.put("belopetgjelder", SentenceBuilder.noun_Phrase());
        map.put("utletternavn", f.getLastName());
        map.put("utlfornavn", f.getFirstName());
        map.put("utlland", f.getCountry().getCode());
        map.put("utladresse", f.getAdresse());
        map.put("utlpoststed", f.getCity());
        map.put("utlpostnr", f.getPostcode());
        map.put("utlkontoland", f.getCountry().getCode());
        map.put("location", f.getCountry().getLocation());
        map.put("nokbelopstr", String.format("%.2f", amountNo));
        map.put("valutabelopstr", String.format("%.2f", map.get("nokbelop")));
        map.put("vrtransid", ""+RandomUtil.getNumber(1000000000));

        map.put("phonnoretternavn", rev(p.getLastName()).toLowerCase());
        map.put("phonnorfornavn", rev(p.getFirstName()).toLowerCase());
        map.put("phonutletternavn", rev(f.getLastName()).toLowerCase());
        map.put("phonutlfornavn", rev(f.getFirstName()).toLowerCase());
        return gson.toJson(map);
    }

    private String rev(String s) {
        if (s == null) return "";
        return new StringBuilder(s).reverse().toString();
    }

}
