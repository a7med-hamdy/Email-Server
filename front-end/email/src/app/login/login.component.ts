import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  url = "http://localhost:8080";
  public loginForm !: FormGroup
  error!: string;

  //constructor
  constructor(private http: HttpClient,
              private formBuilder: FormBuilder,
              private router: Router) {}

  // for login component
  ngOnInit(): void{
    this.loginForm = this.formBuilder.group({
      userName: [''],
      password: [''],
    });
  }

  // log in - request
  logIn(){
    let _url = `${this.url}/login-${this.loginForm.value.userName}-${this.loginForm.value.password}`;
    return this.http.get<any>(_url)
    .subscribe(done => {
      console.log("Logged in successfully!!")
      if(done){
        this.loginForm.reset();
        this.router.navigate(['main']) //navigate to user's home page
      }
      else{
        this.error = "Username or password is incorrect!!"
      }
    },/* err => {
      alert("something went WRONG!!")
    } */)
  }


}
