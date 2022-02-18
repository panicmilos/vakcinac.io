declare namespace zah="https://www.vakcinac-io.rs/zahtev";
import module namespace functx="http://www.functx.com";

<documents>
{
    for $document in collection("/db/zahtevi")
        let $processed := $document//*:meta[@property = 'rdfzzizs:prihvacen']
        where not($processed)
        let $about := $document//@about/string()
        let $createdAt := $document//*:meta[@property = 'rdfos:izdat']/text()
        return <document createdAt="{$createdAt}">{$about}</document>
}
</documents>