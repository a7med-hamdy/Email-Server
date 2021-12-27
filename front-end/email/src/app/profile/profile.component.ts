import { ActivatedRoute, Router } from '@angular/router';
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
  userID!:string;
  constructor(private rs: RequestsService, public router: Router,  public route:ActivatedRoute,private fb: FormBuilder) { }

  contact?:any[];

  newContactForm = this.fb.group({
    name: [''],
    emails: ['']
  })
  ngOnviewInit(){
    this.getcontact();

  }
  ngOnInit(): void {
    this.extractId();
    this.getcontact();
  }
  dataSource!: MatTableDataSource<contact>;
  selectedcontact?:string
  here1:Boolean=false;
  main:Boolean=true;
  here2:Boolean=false;
  displayedColumns: string[] = ["select", "ID", "name","email","userName"];
  selection = new SelectionModel<contact>(true, []);
  added?: string;
  addingContact = false

  back():void {
    this.here1=false;
    this.main=true;
    this.here2=false;
  }
  public extractId(){
      this.route.queryParams.subscribe(params =>{
        this.userID = params["ID"];
        console.log(this.userID);
       })
    }
  getcontact():void{
    this.here1=true;
    this.main=false;
    this.here2=false;
    this.rs.getContacts(this.userID).subscribe(done => {
      this.dataSource = new MatTableDataSource<contact>(done);
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
    this.addingContact = false;
  }

  getAddContact(){
    this.addingContact = true;
  }
  cancelAddContact(){
    this.addingContact = false;
  }

  /** Whether the number of selected elements matches the total number of rows. */
  isAllSelected() {
    const numSelected = this.selection.selected.length;
    const numRows = this.dataSource.data.length;
    return numSelected === numRows;
  }

  /** Selects all rows if they are not all selected; otherwise clear selection. */
  masterToggle() {
    if (this.isAllSelected()) {
      this.selection.clear();
      return;
    }

    this.selection.select(...this.dataSource.data);
  }

  /** The label for the checkbox on the passed row */
  checkboxLabel(row?: contact): string {
    console.log(this.selection.selected)
    if (!row) {
      return `${this.isAllSelected() ? 'deselect' : 'select'} all`;
    }
    return `${this.selection.isSelected(row) ? 'deselect' : 'select'} row ${row.ID}`;
  }
}


export class contact{
  name?: string
  ID?: number
  userName?: string
  email?: string[]
}