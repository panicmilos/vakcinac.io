xquery version "3.1";

declare namespace dig="https://www.vakcinac-io.rs/digitalni-sertifikat";
import module namespace functx="http://www.functx.com";

declare function local:search($keyword as xs:string)
{
    let $kolekcija := collection("/db/digitalni-sertifikati")
    let $sertifikati :=
    (
        $kolekcija//dig:digitalni-sertifikat[contains(., $keyword)],
        $kolekcija//dig:digitalni-sertifikat[contains(@broj-sertifikata, $keyword)],
        $kolekcija//dig:digitalni-sertifikat[contains(@datum-izdavanja, $keyword)]
    )

 return
    functx:distinct-nodes($sertifikati)
};

<documents>
{
    for $document in local:search("%s")
        let $about := $document//@about/string()
        let $createdAt := $document//*:meta[@property = 'rdfos:izdat']/text()
        return <document><url>{$about}</url><created-at>{$createdAt}</created-at></document>
}
</documents>