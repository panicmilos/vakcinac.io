xquery version "3.1";

declare namespace agres="https://www.vakcinac-io.rs/aggregate-result";

declare function local:findAllDose() {
    for $document in collection('/db/potvrde/')
        let $primljeneDoze := $document//*:primljena-doza
        return $primljeneDoze
};

declare function local:constructDate($date as xs:string) as xs:string {
    let $day := substring($date, 1, 2)
    let $month := substring($date, 4, 2)
    let $year := substring($date, 7, 4)
    return concat($year, '-' , $month, '-', $day)
};

<agres:aggregate-result>
{
for $primljenaDoza in local:findAllDose()
    let $datumDoze := local:constructDate($primljenaDoza//*:datum/text())
    where $datumDoze >= "%1$s" and $datumDoze <= "%2$s"
    group by $doza := $primljenaDoza//@redni-broj
    return <agres:group by="{$doza}">{count($primljenaDoza)}</agres:group>
}
</agres:aggregate-result>
