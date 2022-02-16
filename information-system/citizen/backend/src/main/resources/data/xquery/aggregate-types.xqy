xquery version "3.1";

declare namespace agres="https://www.vakcinac-io.rs/aggregate-result";

declare function local:findAllVaccineWithDosesDate() {
    for $document in collection('/db/potvrde/')
        let $nazivVakcine := $document//*:naziv-vakcine/text()
        let $primljeneDoze := $document//*:primljena-doza//*:datum
        return <doze-with-vaccine>
                    <vaccine>{$nazivVakcine}</vaccine>
                    {$primljeneDoze}
                </doze-with-vaccine>
};

declare function local:countValidDoseDates($doseDates) {
    count(for $doseDate in $doseDates
        let $formatedDoseDate := local:constructDate($doseDate)
        where $formatedDoseDate >= "%1$s" and $formatedDoseDate <= "%2$s"
        return $formatedDoseDate)
};

declare function local:constructDate($date as xs:string) as xs:string {
    let $day := substring($date, 1, 2)
    let $month := substring($date, 4, 2)
    let $year := substring($date, 7, 4)
    return concat($year, '-' , $month, '-', $day)
};

declare function local:findAllVaccineWithCount() {
    for $vaccineWithDoses in local:findAllVaccineWithDosesDate()
        let $validDoses := local:countValidDoseDates($vaccineWithDoses//*:datum/text())
        return <doze-with-vaccine>
                    {$vaccineWithDoses//*:vaccine}
                    <count>{$validDoses}</count>
                </doze-with-vaccine>
};

<agres:aggregate-result>
{
for $vaccineWithCount in local:findAllVaccineWithCount()
    let $count := $vaccineWithCount//count
    group by $vaccine := $vaccineWithCount//vaccine
    return <agres:group by="{$vaccine}">{sum($count)}</agres:group>
}
</agres:aggregate-result>

