import { Component, OnInit } from '@angular/core';
import { RequestsService } from '../requests/requests.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  constructor(private rs: RequestsService) { }

  contact?:any[];
  ngOnInit(): void {

  }

  selectedcontact?:string
  here1:Boolean=false;
  main:Boolean=true;
  here2:Boolean=false;


  back():void {
    this.here1=false;
    this.main=true;
    this.here2=false;
  }

  getcontact():void{
    this.here1=true;
    this.main=false;
    this.here2=false;
    this.rs.getContacts().subscribe(done => {
      console.log(done);
      this.contact=done;
      console.log(this.contact);
    },err => {alert("something went WRONG!!")}
    );
    console.log(this.contact);
  }

  getFolder():void{
    this.here1=false;
    this.main=false;
    this.here2=true;
  }
  onSelect():void{

  }

}
