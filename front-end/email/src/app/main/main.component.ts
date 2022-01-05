import { RequestsService } from './../requests/requests.service';
import { query } from '@angular/animations';
import { Component, OnInit, ViewChild } from '@angular/core';
import {ActivatedRoute, NavigationEnd, NavigationStart, Router} from "@angular/router";
import { SelectionModel } from '@angular/cdk/collections';
import { MatTableDataSource } from '@angular/material/table';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';



@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit {
  /**INPUT VARIABLES */

  userID!:string;
  selected:string = '1';
  userSessionID!:string;
  searchForm !: FormGroup;
  searchSelected!:string;
  sortCriteria:string = "date";
  page:number = 1;
  dataSource!: MatTableDataSource<any>;
  selectedRows!: Set<any>;
  selection = new SelectionModel<any>(true, []);
  folder: string = 'inbox' ;

  /**VIEW BOOLEANs */
  search:Boolean=false;
  profile:Boolean=false;
  make:Boolean=false;
  view:Boolean=true;
  filter?:string;

  /**VIEW ARRAYS */
  searchcriteria:string[] = ['global','attachment','sender','receiver','subject','body']
  folders:string[] = [];
  clickedRows:any[] = [];
  displayedColumns: string[] = [' ',"ID", "subject","date"];




  constructor(public route:ActivatedRoute,
              private sanitizer: DomSanitizer,
              public router:Router,
              public req:RequestsService,
              private formBuilder: FormBuilder) {
                //router.navigate(['main']);

              }

  ngOnInit(): void {

    this.router.onSameUrlNavigation ='reload';
    if(sessionStorage.getItem('id') == null){
      this.router.navigate(['/'])
    }
    else{
      this.extractId();
      this.refresh();
      this.routerEventListener();
      this.searchForm = this.formBuilder.group({
        searchField: ['', [Validators.required]],
      });
    }
  }
/**********************listen on Route****************************** */
  /**
   * extract ID from URL
   */
  public extractId(){
    this.route.queryParams.subscribe(params =>{

      this.userID = params["ID"];
      console.log(this.userID);
     })
  }


/**
 * listen on route events
 */
  routerEventListener(){
    this.router.events.subscribe((event) => {
      if(event instanceof NavigationEnd){
      if(this.router.url.includes('Profile')){this.profile1(this.router.url);}
      else if(this.router.url.includes('Search')){this.search1(this.router.url);}
      else if(this.router.url.includes('Create')){this.make1(this.router.url);}
      else{
        this.active(this.router.url);
        this.updateDataSource();
      }
    }
    });

  }



/**********************************DataBase Requests*************************************************** */

public refresh(){
  this.getUserFolders();
  this.updateDataSource();
}
/**
 * requesting user folders to be viewed
 */
public getUserFolders(){
  this.req.getEmailFolders(this.userID).subscribe(response =>{
    this.folders = response;
    console.log(response);
  })
}


  /**
   * request a logout from a session
   */
  Logout(){
    this.router.onSameUrlNavigation = 'reload'
    sessionStorage.removeItem('id')
    this.req.logOut(this.userID);
    this.router.navigate(["/login"])
  }


/**
   *
   * @param type  type of sorting
   */
   sorting(type:string){
      this.sortCriteria = type;
      if(this.search){
        this.searchEmails();
      }
      else{
        this.updateDataSource();
      }
  }


  /**
   * Request Email data from back end
   */
  updateDataSource(){
    (this.req.getEmails(this.folder, this.userID,this.page.toString(),this.sortCriteria)).subscribe(response =>{
      /*if(response == null)
        this.Logout();
        */
      this.dataSource = new MatTableDataSource<any>(response);
      console.log(response);
    });
}


/**
 * Request to move selected items to another folder
 */
MoveSelected(){
  console.log(this.selection.selected)
  this.req.MoveEmail(this.userID,this.selection.selected,this.folder,this.selected,this.page.toString())
  .subscribe(response =>{
    this.dataSource = new MatTableDataSource<any>(response);
    console.log('moved')
  })
  this.selection.clear();
}


/**
 * Request to Delete selected items (move them to trash)
 */
DeleteSelected(){

  this.req.deleteEmail(this.userID,this.selection.selected,this.folder,this.page.toString())
  .subscribe(response => {
    this.dataSource = new MatTableDataSource<any>(response);
    console.log("delete")
  },/* err => {
    alert("something went WRONG!!")
  } */);
  this.selection.clear();

  console.log(this.selection.selected)
}
searchEmails(){
  this.req.getFilteredEmails(this.userID,this.searchSelected,this.searchForm.value.searchField,this.sortCriteria,this.page.toString())
  .subscribe(response =>{
    this.dataSource = new MatTableDataSource<any>(response);
    console.log("Search");
  });
  this.selection.clear();
  console.log(this.searchForm.value.searchField);
  console.log(this.searchSelected);

}


/****************************UI FUNCTIONS *********************************/

  /**view a specific email fucntion
   *
   * @param a row clicked on
   */
   addClickedRows(a:any){
    this.clickedRows = [];
    this.clickedRows.push(a);
    console.log(this.clickedRows);
  }


/****Pagination buttons ******/

/**
 * goto next page
 */
increasePage(){
  this.selection.clear();
  this.page ++;
  if(this.search){
    this.searchEmails();
  }
  else{
    this.updateDataSource();
  }
}


/**
 * goto previous page
 */
decreasePage(){
  this.selection.clear();

  this.page--;
  if(this.page <= 0)
    this.page = 1;
    if(this.search){
      this.searchEmails();
    }
    else{
      this.updateDataSource();
    }
}

/********View buttons***********/



/** toggle folder views
 * folders function
 * @param a
 */
  active(a:string){
    this.view=true
    this.profile=false;
    this.search=false;
    this.make=false;
    this.clickedRows = [];
    for(let i = 0; i < this.folders.length;i++){
      if(a.includes(this.folders[i])){
        this.folder= this.folder.replace(this.folder,this.folders[i]);
      }
    }
  }


/**
 * toggle profile page
 * @param a route
 */
  profile1(a:string){
    this.clickedRows = [];
      this.profile=true;
      this.search=false;
      this.make=false;
      this.view = false;
  }


  /**toggle email creation page
   *
   * @param a route
   */
  make1(a:String){
    this.clickedRows = [];

      this.profile=false;
      this.search=false;
      this.make=true;
      this.view=false;
  }


  /**toggle search page
   *
   * @param a route
   */
  search1(a:String){
    this.clickedRows = [];
      this.profile=false;
      this.search=true;
      this.make=false;
      this.view = false;
  }

onclick(url:string): SafeUrl{
    return this.sanitizer.bypassSecurityTrustResourceUrl(url)
  }


}
