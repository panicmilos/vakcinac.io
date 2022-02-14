xquery version "3.1";

declare function local:findDocumentsThatContains($baseDocumentName as xs:string) {
    for $document in collection('%1$s')
        let $documentName := util:document-name($document)
        where contains($documentName, $baseDocumentName)
        return $documentName
 };


<result>
{
    count(local:findDocumentsThatContains("%2$s"))
}
</result>
