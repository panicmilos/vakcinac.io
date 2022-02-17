xquery version "3.1";

declare namespace sag="https://www.vakcinac-io.rs/saglasnost";
import module namespace functx="http://www.functx.com";

declare function local:search($keyword as xs:string)
{
    let $kolekcija := collection("/db/saglasnosti")
    let $rezultat :=
    (
        $kolekcija//sag:saglasnost-za-sprovodjenje-preporucene-imunizacije[contains(., $keyword)],
        $kolekcija//sag:saglasnost-za-sprovodjenje-preporucene-imunizacije[contains(@datum-izdavanja, $keyword)],
        $kolekcija//sag:saglasnost-za-sprovodjenje-preporucene-imunizacije[contains(@about, $keyword)]
    )

 return
    functx:distinct-nodes($rezultat)
};

<documents>
{
    for $document in local:search("%s")
        let $about := $document//@about/string()
        let $createdAt := $document//*:meta[@property = 'rdfos:izdat']/text()
        return <document createdAt="{$createdAt}">{$about}</document>
}
</documents>