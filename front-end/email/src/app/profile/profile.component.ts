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

  /********************************************************************
   * Contacts
   * 
   ********************************************************************/

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

  
  addContact(){
    let name = this.newContactForm.value.name;
    let emails = this.newContactForm.value.emails;
    this.rs.addContact(this.userID, name, emails)
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
    this.rs.deleteContact(this.userID, this.selection.selected.map(function(a){return a.ID}))
    .subscribe(done =>{
      if(done){
        console.log("Contact(s) deleted successfully")
        this.getcontact() //refresh the contacts list
      }
      else{console.log("Error!! Contact wasn't deleted!")}
    })
  }

  editContact(){
    let contactId = this.selection.selected.map(function(a){return a.ID})[0];
    let oldEmail = this.selection.selected.map(function(a){return a.email})[0] as unknown as string;
    let newEmail = this.newContactForm.controls['emails'].value;
    let oldNmae = this.selection.selected.map(function(a){return a.name}) as unknown as string;
    let newName = this.newContactForm.controls['name'].value;
    this.rs.editContact(this.userID, contactId, oldEmail, newEmail, oldNmae, newName)
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

  /********************************************************************
   * Folders
   * 
   ********************************************************************/
  
  dataSource2!: MatTableDataSource<folder>;
  displayedColumns2: string[] = ["select", "name"];
  selection2 = new SelectionModel<folder>(true, []);
  editingFolder: boolean = false;

  newFolderForm = this.fb.group({
    name: ['']
  })
  addingFolder: boolean = false

  getFolder():void{
    this.here1=false;
    this.main=false;
    this.here2=true;
    this.rs.getFolders(this.userID).subscribe(done => {
      this.dataSource2 = new MatTableDataSource<folder>(done);
      console.log(done);
    }
    );
  }
  addFolder():void{
    let name = this.newFolderForm.value.name;
    this.rs.addFolder(this.userID, name)
    .subscribe( done => {
      if(done){
        console.log("Folder added successfully")
        this.getFolder() //refresh the folder list
        this.newFolderForm.controls['name'].setValue('')
      }
      else{console.log("Error!! Folder wasn't added!")}
    })
  }
  deleteFolder(){ 
    let name = this.selection2.selected.map(function(a){return a.name})[0];
    this.rs.deleteFolder(this.userID, name)
    .subscribe( done => {
      if(done){
        console.log("Folder deleted successfully")
        this.getFolder() //refresh the folder list
      }
      else{console.log("Error!! Folder wasn't deleted!")}
    })
  }
  editFolder(){
    let oldName = this.selection2.selected.map(function(a){return a.name})[0];
    let newName = this.newFolderForm.controls['name'].value;
    this.rs.editFolder(this.userID, oldName, newName)
    .subscribe( done => {
      if(done){
        console.log("Folder edited successfully")
        this.getFolder() //refresh the folder list
      }
      else{console.log("Error!! Folder wasn't edited!")}
    })
  }


  showNewFolderForm(){
    this.addingFolder = true;
  }
  closeNewFolderForm(){
    this.addingFolder = false;
  }

  getFolderEditor(){
    this.editingFolder = true;
  }
  closeFolderEditor(){
    this.editingFolder = false;
  }

  /** Whether the number of selected elements matches the total number of rows. */
  isAllSelected2() {
    const numSelected = this.selection2.selected.length;
    const numRows = this.dataSource2.data.length;
    return numSelected === numRows;
  }

  /** Selects all rows if they are not all selected; otherwise clear selection. */
  masterToggle2() {
    if (this.isAllSelected2()) {
      this.selection2.clear();
      return;
    }

    this.selection2.select(...this.dataSource2.data);
  }

  /** The label for the checkbox on the passed row */
  checkboxLabel2(row?: folder): string {
    // console.log(this.selection.selected)
    if (!row) {
      return `${this.isAllSelected2() ? 'deselect' : 'select'} all`;
    }
    return `${this.selection2.isSelected(row) ? 'deselect' : 'select'} row ${row.name}`;
  }
  
}


export class contact{
  name!: string
  ID!: number
  userName!: string
  email!: string[]
}

export class folder{
  name!: string
}