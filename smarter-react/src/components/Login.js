import React, { Component } from 'react'
import md5 from 'md5'
import { login } from './UserFunctions'

class Login extends Component {
    constructor() {
        super()
        this.state = {
            username: '',
            password: '',
            errors: {}
        }

        this.onChange = this.onChange.bind(this)
        this.onSubmit = this.onSubmit.bind(this)
    }

    onChange(e) {
        this.setState({ [e.target.name]: e.target.value })
    }
    onSubmit(e) {
        e.preventDefault()

       let allCredential = login()
       let checkUsername = allCredential.some(item=>item.username===this.state.username)
       if(checkUsername==true){
           let tempUser = allCredential.filter(x=>x.username===this.state.username)
           if(tempUser.password===md5(this.state.password)) {
                localStorage.setItem('resid', tempUser.resid.resid)
                this.props.history.push(`/profile`)
           }
       }
    }

    /*
      let curUsername=this.state.username
      let curPasswd=this.state.password
      for (let i=0;i<allCredential.length;i++){
          if(allCredential[i].hashasOwnProperty('username') && allCredential[i][username] === this.state.username){
              let tempUser=allCredential[i]
              if(tempUser.password===md5(this.state.password)) {
                //break              
                this.props.history.push(`/profile`)
                break
            }
        }
      }
    */

    render() {
        return (
            <div className="container">
                <div className="row">
                    <div className="col-md-6 mt-5 mx-auto">
                        <form noValidate onSubmit={this.onSubmit}>
                            <h1 className="h3 mb-3 font-weight-normal">
                                Please sign in
                            </h1>
                            <div className="form-group">
                                <label htmlFor="username">Username</label>
                                <input
                                    type="username"
                                    className="form-control"
                                    name="username"
                                    placeholder="User name length is less than 100"
                                    value={this.state.username}
                                    onChange={this.onChange}
                                    required
                                />
                            </div>
                            <div className="form-group">
                                <label htmlFor="password">Password</label>
                                <input
                                    type="password"
                                    className="form-control"
                                    name="password"
                                    placeholder="Password"
                                    value={this.state.password}
                                    onChange={this.onChange}
                                    required
                                />
                            </div>
                            <button
                                type="submit"
                                className="btn btn-lg btn-primary btn-block"
                            >
                                Login
                            </button>
                        </form>
                    </div>
                </div>
            </div>
        )
    }
}

export default Login
