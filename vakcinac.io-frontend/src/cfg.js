export const app = 'CitizenServant';

const CITIZEN_API_URL = 'http://localhost:8880';
const CITIZEN_SERVANT_API_URL = 'http://localhost:8881';

export const API_URL = app === 'Citizen' ? CITIZEN_API_URL : CITIZEN_SERVANT_API_URL;