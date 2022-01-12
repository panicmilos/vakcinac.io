xjc -d . -p vakcinac.io.citizen.models.os -episode osnovna.episode ../osnovna_sema.xsd

xjc -d . -p vakcinac.io.citizen.models.dig -extension -b osnovna.episode ../digitalni_sertifikat.xsd -b dateTime.xjb
xjc -d . -p vakcinac.io.citizen.models.izj -extension -b osnovna.episode ../izjava_interesovanja_za_vakcinisanje.xsd -b date.xjb
xjc -d . -p vakcinac.io.citizen.models.izv -extension -b osnovna.episode ../izvestaj_o_imunizaciji.xsd -b date.xjb
xjc -d . -p vakcinac.io.citizen.models.pot -extension -b osnovna.episode ../potvrda_o_izvrsenoj_vakcinaciji.xsd -b date.xjb
xjc -d . -p vakcinac.io.citizen.models.sag -extension -b osnovna.episode ../saglasnost_imunizacije.xsd -b date.xjb
xjc -d . -p vakcinac.io.citizen.models.zah -extension -b osnovna.episode ../zahtev_za_zeleni_sertifikat.xsd -b date.xjb
