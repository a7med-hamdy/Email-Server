import { query } from '@angular/animations';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { RequestsService } from '../requests/requests.service';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  url = "http://localhost:8080";
  loginForm !: FormGroup
  error!: string;
  userNameIsEmpty = true;
  passwordIsEmpty!: boolean;


  //constructor
  constructor(private http: HttpClient,
              private formBuilder: FormBuilder,
              private router: Router,
              private req:RequestsService) {}

  // for login component
  ngOnInit(): void{
    this.loginForm = this.formBuilder.group({
      userName: ['', [Validators.required, Validators.minLength(3)]],
      password: ['', [Validators.required, Validators.minLength(4)]],
    });
  }
/*********************************Login IN function************************************/
  // log in - request
  /**
   * requests login
   * @returns result of login request
   */
  logIn(){
    let _url = `${this.url}/login-${this.loginForm.value.userName}-${this.loginForm.value.password}`;
    return this.http.get<any>(_url)
    .subscribe(ID => {
      console.log("Log in!!", "userID = ", ID)
      if(ID != 0){
        this.loginForm.reset();
        this.req.getSessionID(ID).subscribe(response =>{
          sessionStorage.setItem('id', response)
          console.log(response);
          console.log(sessionStorage.getItem('id'));
          this.router.navigate([`main`, 'Inbox'], {queryParams: {ID : ID}});
        })
        //navigate to user's home page
      }
      else{
        this.error = "Username or password is incorrect";
      }
    },err => {
      alert("something went WRONG!!" + err)
    })
  }


/**
 * enables login button
 * @returns the validation of a login form True or false
 */
  enableSubmitButton(): boolean{
    return this.loginForm.valid;
  }

}
