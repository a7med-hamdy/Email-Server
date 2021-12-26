import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
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
      userName: ['', [Validators.required, Validators.minLength(3)]],
      email:    ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(4)]],
    })
  }

  // sign up - request
  signUp(){
    return this.http.post<any>(`${this.url}/signUp`, this.signupForm.value)
    .subscribe(ID => {
      console.log("Sign up!!", "userID = ", ID)
      if(ID != 0){
        this.signupForm.reset();
        this.router.navigate([`main`, 'Inbox'], {queryParams: {ID : ID}});//navigate to user's home page
      }
      else {
        this.error = "Invalid input(s)"
      }
    },err => {
      alert("something went WRONG!!" + err)
    })
  }

  enableSubmitButton(): boolean{
    return this.signupForm.valid;
  }
}
