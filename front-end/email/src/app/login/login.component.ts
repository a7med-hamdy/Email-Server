import { Component, OnInit } from '@angular/core';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  value =  "clear me"

  constructor() { }

  ngOnInit(): void {
  }

  onSubmit(){
    console.log("clicked")
  }

}
