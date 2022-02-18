export function isLoggedIn() {
  return localStorage.getItem('jwt') && parseJwt(localStorage.getItem('jwt')).exp < new Date().getTime();
}

export const ROLES = {
  DomaciGradjanin: "ROLE_DomaciGradjanin",
  StraniGradjanin: "ROLE_StraniGradjanin",
	Sluzbenik: "ROLE_Sluzbenik",
  ZdravstveniRadnik: "ROLE_ZdravstveniRadnik",
}

export function getRole() {
  return localStorage.getItem('jwt') && parseJwt(localStorage.getItem('jwt'))?.roles || "";
}

export function getGradjaninId() {
  return localStorage.getItem('jwt') && parseJwt(localStorage.getItem('jwt'))?.GradjaninId || "";
}

function parseJwt(token) {
  var base64Url = token.split('.')[1];
  var base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
  var jsonPayload = decodeURIComponent(atob(base64).split('').map(function(c) {
      return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
  }).join(''));
  return JSON.parse(jsonPayload);
}