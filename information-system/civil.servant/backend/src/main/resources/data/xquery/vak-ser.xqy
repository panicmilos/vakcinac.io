xquery version "3.1";

declare namespace vak="https://www.vakcinac-io.rs/vakcina";
import module namespace functx="http://www.functx.com";

declare function local:search($keyword as xs:string)
{
    let $kolekcija := collection("/db/vakcine")
    let $rezultat :=
    (
        $kolekcija//vak:vakcina[contains(., $keyword)]
    )

 return
    functx:distinct-nodes($rezultat)
};

local:search("%s")