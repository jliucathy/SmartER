import axios from 'axios'

export const API_BASE_URL='http://localhost:5003/'

export const registerRes = newResident => {
    return axios
        .post(API_BASE_URL+'api/createResident', newResident, {
            headers: { 'Content-Type': 'application/json' }
        })
        .then(response => {
            console.log(response.data.code)
            return response.data.code
        })
        .catch(err => {
            console.log(err)
        })
}

export const registerCre = newCredential => {
    return axios
        .post(API_BASE_URL+'api/createCredential', newCredential, {
            headers: { 'Content-Type': 'application/json' }
        })
        .then(response => {
            console.log(response.data.code)
            return response.data.code
        })
        .catch(err => {
            console.log(err)
        })
}

export const login = () => {
    return axios
        .get(
            API_BASE_URL+'api/findAllCredential',
            {
                headers: { 'Content-Type': 'application/json' }
            }
        )
        .then(response => {
            console.log(response)
            return response.data
        })
        .catch(err => {
            console.log(err)
        })
}

export const getProfile = () => {
    return axios
        .get(API_BASE_URL+'/api/findUsageByResid/'+localStorage.resid, 
        {
            headers: { 'Content-Type': 'application/json' }
        })
        .then(response => {
            console.log(response)
            return response.data
        })
        .catch(err => {
            console.log(err)
        })
}
