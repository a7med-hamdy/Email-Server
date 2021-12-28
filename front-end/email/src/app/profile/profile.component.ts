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
  addContactBtn = true
  addContactDiv = false
  editing = false

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
    this.cancelAddContact();
  }

  deleteContact(){
    this.rs.deleteContact(this.selection.selected.map(function(a){return a.ID}))
    .subscribe(done =>{
      if(done){
        console.log("Contact(s) deleted successfully")
        this.getcontact() //refresh the contacts list
      }
      else{console.log("Error!! Contact wasn't deleted!")}
    })
  }

  editContact(){
    this.rs.editContact(this.selection.selected.map(function(a){return a.ID})[0],
                        this.selection.selected.map(function(a){return a.email})[0] as unknown as string,
                        this.newContactForm.controls['emails'].value,
                        this.selection.selected.map(function(a){return a.name}) as unknown as string,
                        this.newContactForm.controls['name'].value)
    .subscribe(done => {
      if(done){
        console.log("Contact edited successfully")
        this.getcontact() //refresh the contacts list
      }
      else{console.log("Error!! Contact wasn't edited!")}
    })
  }

  getAddContact(){
    this.addContactBtn = !this.addContactBtn;
    this.addContactDiv = !this.addContactDiv;
  }
  cancelAddContact(){
    this.addContactBtn = !this.addContactBtn;
    this.addContactDiv = !this.addContactDiv;
    this.resetContactForm();
  }
  confirmAddBtn(){
    return this.newContactForm.controls['name'].value.length!==0 && this.newContactForm.controls['emails'].value.length!==0;
  }

  getEditor(){
    this.editing = true;
    this.newContactForm.controls['name'].setValue(this.selection.selected.map(function(a){return a.name}));
    let email = this.selection.selected.map(function(a){return a.email});
    let emails  = (email[0] as unknown as string).replace('[', '').replace(']', '');
    this.newContactForm.controls['emails'].setValue(emails);
    this.addContactBtn = false;
  }
  closeEditor(){
    this.editing = false;
    this.resetContactForm();
    this.addContactBtn = true;
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
    // console.log(this.selection.selected)
    if (!row) {
      return `${this.isAllSelected() ? 'deselect' : 'select'} all`;
    }
    return `${this.selection.isSelected(row) ? 'deselect' : 'select'} row ${row.ID}`;
  }

  resetContactForm(){
    this.newContactForm.controls['name'].setValue('');
    this.newContactForm.controls['emails'].setValue('');
  }
}


export class contact{
  name!: string
  ID!: number
  userName!: string
  email!: string[]
}