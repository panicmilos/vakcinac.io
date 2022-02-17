xquery version "3.1";

declare variable $currentDateTime := xs:dateTime("%1$s");
declare variable $citizenId := "%2$s";

declare function local:constructDateTime($dateTime as xs:string) as xs:dateTime {
    let $day := substring($dateTime, 1, 2)
    let $month := substring($dateTime, 4, 2)
    let $year := substring($dateTime, 7, 4)
    
    let $hoursMinutes := substring($dateTime, 13, 5)

    return xs:dateTime(concat($year, '-' , $month, '-', $day, 'T', $hoursMinutes, ':00'))
};

(for $termin in collection('/db/termini/')//*:termin
    let $dateTime := local:constructDateTime($termin//*:vreme/text())
    where $dateTime <= $currentDateTime
	order by $dateTime
    let $jmbg := $termin//*:jmbg/text()
	where $jmbg = $citizenId
	let $documentName := util:document-name($termin)
    return $documentName)[last()]
