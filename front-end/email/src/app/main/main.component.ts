import { RequestsService } from './../requests/requests.service';
import { query } from '@angular/animations';
import { Component, OnInit, ViewChild } from '@angular/core';
import {ActivatedRoute, NavigationEnd, NavigationStart, Router} from "@angular/router";
import { SelectionModel } from '@angular/cdk/collections';
import { MatTableDataSource } from '@angular/material/table';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';



@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit {
  userID!:string;
  selected:string = '1';
  userSessionID!:string;
  /**VIEW BOOLEANs */
  search:Boolean=false;
  profile:Boolean=false;
  make:Boolean=false;
  view:Boolean=true;
  filter?:string;

  folders:string[] = [];
  clickedRows:any[] = [];
  displayedColumns: string[] = [' ',"ID", "subject","date"];
  page:number = 1;
  dataSource!: MatTableDataSource<any>;
  selectedRows!: Set<any>;
  selection = new SelectionModel<any>(true, []);
  folder: string = 'inbox' ;



  constructor(public route:ActivatedRoute,
              private sanitizer: DomSanitizer,
              public router:Router,
              public req:RequestsService) {
                //router.navigate(['main']);

              }

  ngOnInit(): void {
    this.router.onSameUrlNavigation ='reload';
    this.extractId();
    this.getUserFolders();
    this.updateDataSource();
    this.routerEventListener();

  }
/**********************listen on Route****************************** */
  /**
   * extract ID from URL
   */
  public extractId(){
    this.route.queryParams.subscribe(params =>{
      if(sessionStorage.getItem('id') == null){
        this.router.navigate(['/login'])
      }
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
    this.req.logOut(this.userID);
    this.router.navigate(["/login"])
  }


/**
   *
   * @param type  type of sorting
   */
   sorting(type:string){
    (this.req.getEmails(this.folder, this.userID,this.page.toString(),type)).subscribe(response =>{
      /*if(response == null)
        this.Logout();
        */
      this.dataSource = new MatTableDataSource<any>(response);
      console.log(response);
    });
  }


  /**
   * Request Email data from back end
   */
  updateDataSource(){
    (this.req.getEmails(this.folder, this.userID,this.page.toString(),"date")).subscribe(response =>{
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
  this.updateDataSource();
}


/**
 * goto previous page
 */
decreasePage(){
  this.selection.clear();

  this.page--;
  if(this.page <= 0)
    this.page = 1;
  this.updateDataSource();
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
    if(a.includes('Profile')){

      this.profile=true;
      this.search=false;
      this.make=false;
      this.view = false;
    }
  }


  /**toggle email creation page
   *
   * @param a route
   */
  make1(a:String){
    if(a.includes('Create')){
      this.profile=false;
      this.search=false;
      this.make=true;
      this.view=false;
    }
  }


  /**toggle search page
   *
   * @param a route
   */
  search1(a:String){
    if(a.includes('Search')){
      this.profile=false;
      this.search=true;
      this.make=false;
      this.view = false;
    }
  }

onclick(url:string): SafeUrl{
    return this.sanitizer.bypassSecurityTrustResourceUrl(url)
  }


}
