import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {
  value!: string;
  url = "http://localhost:8080";
  public signupForm !: FormGroup
  error!: string;

  //constructor
  constructor(private http: HttpClient,
    private formBuilder: FormBuilder,
    private router: Router) {}
    
  // for signup component
  ngOnInit(): void{
    this.signupForm = this.formBuilder.group({
      userName: [''],
      email:    [''],
      password: [''],
    })
  }

  // sign up - request
  signUp(){
    return this.http.post<any>(`${this.url}/signUp`, this.signupForm.value)
    .subscribe(response => {
      console.log("Signed up successfully!!")
      this.signupForm.reset();
    },/* err => {
      alert("something went WRONG!!")
    } */)
  }
}
