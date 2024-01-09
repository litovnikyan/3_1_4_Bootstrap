const urlUser = 'http://localhost:8080/api/user'
const navbarBrandUser = document.getElementById('navbarBrandUser'); //header
const tableAuthUser = document.getElementById('tableAuthUser');//table

function showCurrentUser() {
    console.log('Загружаю данные пользователя...')
    fetch(urlUser)
        .then((res) => res.json())
        .then((data) => {
            showUserInfo(data)
        });
    console.log('Информация о пользователе загружена')
}

function showUserInfo(user) {
    let result = ''
    result += `
         <tr>
                        <td>${user.id}</td>
                        <td>${user.firstName}</td>
                        <td>${user.lastName}</td>
                        <td>${user.age}</td>
                        <td>${user.email}</td>                      
                       <td>${listRoles(user)}</td>
                        </tr>`
    tableAuthUser.innerHTML = result
    navbarBrandUser.innerHTML = `<b><span>${user.email}</span></b>
                             <span>with roles:</span>
                             <span>${listRoles(user)}</span>`

}

function listRoles(user) {
    let roles = "";
    for (let i = 0; i < user.roleSet.length; i++) {
        roles += " " + user.roleSet[i].name.substring(5)
    }
    return roles;
}

showCurrentUser()