import { Router } from '@angular/router';
import { SelectionModel } from '@angular/cdk/collections';
import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { RequestsService } from '../requests/requests.service';
import { FormBuilder } from '@angular/forms';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  constructor(private rs: RequestsService, public router: Router, private fb: FormBuilder) { }

  contact?:any[];

  newContactForm = this.fb.group({
    name: [''],
    emails: ['']
  })
  ngOnviewInit(){
    this.getcontact();

  }
  ngOnInit(): void {
    this.getcontact();
  }
  dataSource!: MatTableDataSource<any>;
  selectedcontact?:string
  here1:Boolean=false;
  main:Boolean=true;
  here2:Boolean=false;
  displayedColumns: string[] = ["ID", "name","email","userName"];
  selection = new SelectionModel<number>(true, []);
  added?: string;

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
      this.dataSource = new MatTableDataSource<any>(done);
      console.log(done);
    }
    );

  }

  getFolder():void{
    this.here1=false;
    this.main=false;
    this.here2=true;
  }
  addFolder():void{
    this.rs.addFolder("555","aaa")
  }

  addContact(){
    let name = this.newContactForm.value.name;
    let emails = this.newContactForm.value.emails;
    this.rs.addContact(name, emails)
    .subscribe(done => {
      if(done){
        console.log("Contact added successfully")
        this.getcontact() //refresh the contacts list
      }
      else{console.log("Error!! Contact wasn't added!")}
    })
  }
}
