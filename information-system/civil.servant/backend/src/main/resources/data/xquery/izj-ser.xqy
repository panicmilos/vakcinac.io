xquery version "3.1";

declare namespace izj="https://www.vakcinac-io.rs/interesovanje";
import module namespace functx="http://www.functx.com";

declare function local:search($keyword as xs:string)
{
    let $kolekcija := collection("/db/izjave-interesovanja")
    let $rezultat :=
    (
        $kolekcija//izj:izjava-interesovanja-za-vakcinisanje[contains(., $keyword)],
        $kolekcija//izj:izjava-interesovanja-za-vakcinisanje[contains(@dan, $keyword)]
    )

 return
    functx:distinct-nodes($rezultat)
};

local:search("%s")