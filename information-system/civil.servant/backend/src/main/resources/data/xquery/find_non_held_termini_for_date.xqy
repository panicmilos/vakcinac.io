xquery version "3.1";

declare namespace xsi="http://www.w3.org/2001/XMLSchema-instance";

declare variable $maxDateTime := %1$s;
declare variable $currentDay := %2$s;

declare function local:constructDateTime($dateTime as xs:string) as xs:dateTime {
    let $day := substring($dateTime, 1, 2)
    let $month := substring($dateTime, 4, 2)
    let $year := substring($dateTime, 7, 4)

    let $hoursMinutes := substring($dateTime, 13, 5)

    return xs:dateTime(concat($year, '-' , $month, '-', $day, 'T', $hoursMinutes, ':00'))
};

for $termin in collection(concat('/db/termini/', $currentDay))//:termin
    let $dateTime := local:constructDateTime($termin//:vreme/text())
    where $maxDateTime > $dateTime
    let $isRealized := $termin//*:realizovan[@xsi:nil = true()]
    return $termin