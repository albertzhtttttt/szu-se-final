const TokenKey = 'vue_admin_template_token'

export function getToken() {
  return localStorage.getItem(TokenKey)
}

export async function setToken(token: string) {
  localStorage.setItem(TokenKey, token)
}

export function removeToken() {
  localStorage.removeItem(TokenKey)
}
