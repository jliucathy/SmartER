import React, { Component } from 'react'
import md5 from 'md5'
import DatePicker from 'react-datepicker'
import { registerRes, registerCre } from './UserFunctions'

import 'react-datepicker/dist/react-datepicker.css'
import 'bootstrap/dist/css/bootstrap.min.css'

class Register extends Component {
    constructor() {
        super()
        this.state = {
            username:'',
            first_name: '',
            last_name: '',
            dob:new Date(),
            address:'',
            postcode:'',
            email: '',
            mobile:'',
            noofresidents:'',
            energyprovider:'',
            password: '', 
            repeatPasswd:'',          
            errors: {}
        }

        this.onChange = this.onChange.bind(this)
        this.onSubmit = this.onSubmit.bind(this)
        this.onDateChange=this.onDateChange.bind(this)
    }

    onChange(e) {
        this.setState({ [e.target.name]: e.target.value })
    }
    onDateChange(date){
        this.setState({
            dob:date
        })
    }
    onSubmit(e) {
        e.preventDefault()

        let currentDate=new Date().getDate().toLocaleString()      
        let registerYear=currentDate.substring(6,10)
        let registerMonth=currentDate.substring(3,5)
        let registerDate=currentDate.substring(0,2)

        const registrationdate=registerYear+"-"+registerMonth+"-"+registerDate

        let chosenDate = this.state.dob
        let parDate = new Date(chosenDate)
        let chosenMonth=parDate.getMonth()+1
        let chosenDay=parDate.getDate()
        let chosenYear=parDate.getFullYear()
        if (chosenMonth.length<2){
            chosenMonth='0'+chosenMonth
        }
        if (chosenDay.length<2){
            chosenDay='0'+chosenDay
        }
        const dateOfBirth=chosenYear+"-"+chosenMonth+"-"+chosenDay
        
        const newResident = {
            firstname: this.state.first_name,
            surname: this.state.last_name,  
            dob:dateOfBirth,
            address:this.state.address,
            postcode:this.state.postcode,
            email: this.state.email,
            mobile:this.state.mobile,
            noofresidents:this.state.noofresidents,
            energyprovider:this.state.energyprovider
        }
        const newCredential = {
                username:this.state.username,
                resid:{
                    firstname: this.state.first_name,
                    surname: this.state.last_name,  
                    dob:chosenDate.format('L'),
                    address:this.state.address,
                    postcode:this.state.postcode,
                    email: this.state.email,
                    mobile:this.state.mobile,
                    noofresidents:this.state.noofresidents,
                    energyprovider:this.state.energyprovider
                },
                Password: md5(this.state.password.trim()),
                registrationdate:registrationdate
            }
        let resReturnCode
        let creReturnCode  
        let regex = /[a-zA-Z0-9]+/g //password contains numbers and letters
        if (this.state.password===this.state.repeatPasswd
            && this.state.first_name.length<=100
            && this.state.surname.length<=100
            && this.state.energyprovider.length<=100
            && this.state.noofresidents>=1
            && this.state.postcode.length===4//postcode should only be with 4 int
            && this.state.mobile.substring(0,2)==="04" //mobile number start with 04
            && this.state.password.match(regex)
            ){
            resReturnCode = registerRes(newResident)
            if (resReturnCode===200){
                creReturnCode = registerCre(newCredential)   
            }
        } 
            
        if(resReturnCode===200 && creReturnCode===200){
              this.props.history.push(`/login`)
          }
    }

    render() {
        return (
            <div className="container">
                <div className="row">
                    <div className="col-md-6 mt-5 mx-auto">
                        <form noValidate onSubmit={this.onSubmit}>
                            <h1 className="h3 mb-3 font-weight-normal">
                                Register
                            </h1>
                            <div className="form-group">
                                <label htmlFor="username">Username</label>
                                <input
                                    type="text"
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
                                    placeholder="Password need both letters and numbers"
                                    value={this.state.password}
                                    onChange={this.onChange}
                                    required
                                />
                            </div>
                            <div className="form-group">
                                <label htmlFor="password">Repeat Password</label>
                                <input
                                    type="password"
                                    className="form-control"
                                    name="password"
                                    placeholder="Same with password"
                                    value={this.state.repeatPasswd}
                                    onChange={this.onChange}
                                    required
                             />
                            </div>
                            <div className="form-group">
                                <label htmlFor="name">First name</label>
                                <input
                                    type="text"
                                    className="form-control"
                                    name="first_name"
                                    placeholder="Enter your first name"
                                    value={this.state.first_name}
                                    onChange={this.onChange}
                                    required
                                />
                            </div>
                            <div className="form-group">
                                <label htmlFor="name">Last name</label>
                                <input
                                    type="text"
                                    className="form-control"
                                    name="last_name"
                                    placeholder="Enter your last name"
                                    value={this.state.last_name}
                                    onChange={this.onChange}
                                />
                            </div>
                            <div className="form-group">
                                <label htmlFor="dob">Date of Birth</label>
                                <p></p>
                                <DatePicker                               
                                    selected={this.state.dob}
                                    onChange={this.onDateChange}
                                    name="dob"  
                                    dateFormat="yyyy-MM-dd"  
                                    showTimeInput                                  
                                />
                            </div>
                            <div className="form-group">
                                <label htmlFor="addreess">Address</label>
                                <input
                                    type="text"
                                    className="form-control"
                                    name="address"
                                    placeholder="Address length is less than 256"
                                    value={this.state.address}
                                    onChange={this.onChange}
                                    required
                                />
                            </div>
                            <div className="form-group">
                                <label htmlFor="postcode">Postcode</label>
                                <input
                                    type="text"
                                    className="form-control"
                                    name="postcode"
                                    placeholder="Postcode length is 4, start with 1-9"
                                    value={this.state.postcode}
                                    onChange={this.onChange}
                                />
                            </div>
                            <div className="form-group">
                                <label htmlFor="email">Email address</label>
                                <input
                                    type="email"
                                    className="form-control"
                                    name="email"
                                    placeholder="Enter email"
                                    value={this.state.email}
                                    onChange={this.onChange}
                                />
                            </div>  
                            <div className="form-group">
                                <label htmlFor="mobile">Mobile</label>
                                <input
                                    type="text"
                                    className="form-control"
                                    name="mobile"
                                    placeholder="Mobile number starts with 04"
                                    value={this.state.mobile}
                                    onChange={this.onChange}
                                    required
                                />
                            </div> 
                            <div className="form-group">
                                <label htmlFor="noofresidents">No of Residents</label>
                                <input
                                    type="text"
                                    className="form-control"
                                    name="noofresidents"
                                    placeholder="Please type a number not less than 1"
                                    value={this.state.noofresidents}
                                    onChange={this.onChange}
                                    required
                                />
                            </div>  
                            <div className="form-group">
                                <label htmlFor="energyprovider">Energy Provider</label>
                                <input
                                    type="text"
                                    className="form-control"
                                    name="energyprovider"
                                    placeholder="The name of your energy provider"
                                    value={this.state.energyprovider}
                                    onChange={this.onChange}
                                    required
                                />
                            </div>             
                            <button
                                type="submit"
                                className="btn btn-lg btn-primary btn-block"
                            >
                                Register!
                            </button>
                        </form>
                    </div>
                </div>
            </div>
        )
    }
}

export default Register
