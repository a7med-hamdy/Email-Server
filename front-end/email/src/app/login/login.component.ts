import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';


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
              private router: Router) {}

  // for login component
  ngOnInit(): void{
    this.loginForm = this.formBuilder.group({
      userName: ['', [Validators.required, Validators.minLength(3)]],
      password: ['', [Validators.required, Validators.minLength(4)]],
    });
  }

  // log in - request
  logIn(){
    let _url = `${this.url}/login-${this.loginForm.value.userName}-${this.loginForm.value.password}`;
    return this.http.get<any>(_url)
    .subscribe(ID => {
      console.log("Log in!!", "userID = ", ID)
      if(ID != 0){
        this.loginForm.reset();
        this.router.navigate([`main/${ID}`]) //navigate to user's home page
      }
      else{
        this.error = "Username or password is incorrect";
      }
    },err => {
      alert("something went WRONG!!" + err)
    })
  }

  enableSubmitButton(): boolean{
    return this.loginForm.valid;
  }

}
