xquery version "3.1";

declare namespace izv="https://www.vakcinac-io.rs/izvestaj";
import module namespace functx="http://www.functx.com";

declare function local:search($keyword as xs:string)
{
    let $kolekcija := collection("/db/izvestaji")
    let $rezultat :=
    (
        $kolekcija//izv:izvestaj-o-imunizaciji[contains(., $keyword)],
        $kolekcija//izv:izvestaj-o-imunizaciji[contains(@izdato, $keyword)],
        $kolekcija//izv:izvestaj-o-imunizaciji[contains(@od, $keyword)],
        $kolekcija//izv:izvestaj-o-imunizaciji[contains(@do, $keyword)],
        $kolekcija//izv:izvestaj-o-imunizaciji[contains(@about, $keyword)]
    )

 return
    functx:distinct-nodes($rezultat)
};

local:search("%s")