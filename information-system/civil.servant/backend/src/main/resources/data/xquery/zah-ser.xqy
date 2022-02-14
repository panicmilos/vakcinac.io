xquery version "3.1";

declare namespace zah="https://www.vakcinac-io.rs/zahtev";
import module namespace functx="http://www.functx.com";

declare function local:search($keyword as xs:string)
{
    let $kolekcija := collection("/db/zahtevi")
    let $rezultat :=
    (
        $kolekcija//zah:zahtev-za-izdavanje-zelenog-sertifikata[contains(., $keyword)],
        $kolekcija//zah:zahtev-za-izdavanje-zelenog-sertifikata[contains(@dan, $keyword)],
        $kolekcija//zah:zahtev-za-izdavanje-zelenog-sertifikata[contains(@mesto, $keyword)],
        $kolekcija//zah:zahtev-za-izdavanje-zelenog-sertifikata[contains(@about, $keyword)]
    )

 return
    functx:distinct-nodes($rezultat)
};

local:search("%s")