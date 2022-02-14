xquery version "3.1";

declare namespace pot="https://www.vakcinac-io.rs/potvrda";
import module namespace functx="http://www.functx.com";

declare function local:search($keyword as xs:string)
{
    let $kolekcija := collection("/db/potvrde")
    let $rezultat :=
    (
        $kolekcija//pot:potvrda-o-izvrsenoj-vakcinaciji[contains(., $keyword)],
        $kolekcija//pot:potvrda-o-izvrsenoj-vakcinaciji[contains(@sifra-potvrde, $keyword)],
        $kolekcija//pot:potvrda-o-izvrsenoj-vakcinaciji[contains(@broj-primljenih-doza, $keyword)]
    )

 return
    functx:distinct-nodes($rezultat)
};

local:search("%s")