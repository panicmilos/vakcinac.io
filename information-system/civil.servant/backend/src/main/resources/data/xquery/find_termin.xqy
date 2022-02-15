xquery version "3.1";

declare variable $numOfZR := %1$s;

declare variable $maxNumOfTermins := $numOfZR * 48;

declare variable $totalNumOfTermins := count(local:findDocumentsThatContains("%2$s", ".xml"));

declare function local:findDocumentsThatContains($baseDocumentPath as xs:string, $baseDocumentName as xs:string) {
    for $document in collection(concat('/db/termini/', $baseDocumentPath))
        let $documentName := util:document-name($document)
        where contains($documentName, $baseDocumentName)
        return $documentName
};

if($totalNumOfTermins != $maxNumOfTermins) then
    (
        for $i in (0 to 47)
            let $totalTerminsInCurrentPeriod := count(local:findDocumentsThatContains("%2$s", concat("termin_", $i)))
            return if($totalTerminsInCurrentPeriod != $numOfZR) then $i else ()
    )[1]
else
    -1