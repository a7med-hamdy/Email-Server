import { RequestsService } from './../requests/requests.service';
import { query } from '@angular/animations';
import { Component, OnInit, ViewChild } from '@angular/core';
import {ActivatedRoute, NavigationEnd, NavigationStart, Router} from "@angular/router";
import { ViewComponent } from '../view/view.component';
import { MatPaginator } from '@angular/material/paginator';
import { SelectionModel } from '@angular/cdk/collections';
import { MatTableDataSource } from '@angular/material/table';



@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit {
  userID:any;
  selected?: string;
  search:Boolean=false;
  profile:Boolean=false;
  make:Boolean=false;
  view:Boolean=true;
  viewI:Boolean=true;
  viewS:Boolean=false;
  viewD:Boolean=false;
  viewT:Boolean=false;
  filter?:string;
  clickedRows = new Set<any>();
  displayedColumns: string[] = [' ',"ID", "subject","body","date", "priority"];
  page:number = 1;
  dataSource!: MatTableDataSource<any>;
  selection = new SelectionModel<number>(true, []);
  @ViewChild('paginator') paginator!: MatPaginator;
  folder: string = 'inbox' ;



  constructor(public route:ActivatedRoute,
              public router:Router,
              public req:RequestsService) {
                //router.navigate(['main']);

              }
  public extractId(){
    this.route.queryParams.subscribe(params =>{
      this.userID = params['ID'];
      console.log(this.userID);
     })
  }
  Logout(){
    this.req.logOut(this.userID);
    this.router.navigate(["/login"])
  }
  ngOnInit(): void {
    this.extractId();
    this.updateDataSource();
    this.routerEventListener();
  }
  increasePage(){
    this.page ++;
    this.updateDataSource();
  }
  decreasePage(){
    this.page--;
    if(this.page <0)
      this.page = 1;
    this.updateDataSource();
  }
  routerEventListener(){
    this.router.events.subscribe((event) => {
      if(event instanceof NavigationEnd){
      if(this.router.url.includes('Inbox')
      || this.router.url.includes('Deleted')
      || this.router.url.includes('Sent')
      || this.router.url.includes('Drafted')){
        this.active(this.router.url);
        console.log(this.userID);
        this.updateDataSource();
      }

      if(this.router.url.includes('Profile')){this.profile1(this.router.url);}
      if(this.router.url.includes('Search')){this.search1(this.router.url);}
      if(this.router.url.includes('Create')){this.make1(this.router.url);}
    }
    });

  }
  addClickedRows(a:any){
    this.clickedRows.add(a);
    console.log(this.clickedRows);
  }

  updateDataSource(){
    (this.req.getEmails(this.folder, this.userID,this.page.toString())).subscribe(response =>{
      this.dataSource = new MatTableDataSource<any>(response);
      console.log(response);
    });
}
  active(a:string){
    this.view=true
    this.profile=false;
    this.search=false;
    this.make=false;
    if(a.includes('Inbox')){
      this.folder = this.folder.replace(this.folder, "inbox")
      console.log(this.folder);
      this.viewI=true;
      this.viewS=false;
      this.viewD=false;
      this.viewT=false;
    }
    else if(a.includes('Sent')){
      this.folder = this.folder.replace(this.folder, "sent")
      this.viewS=true;
      this.viewI=false;
      this.viewD=false;
      this.viewT=false;
    }
    else if(a.includes('Drafted')){
      this.folder = this.folder.replace(this.folder, "draft")
      this.viewD=true;
      this.viewI=false;
      this.viewS=false;
      this.viewT=false;
    }
    else if(a.includes('Deleted')){
      this.folder = this.folder.replace(this.folder, "trash")
      this.viewT=true;
      this.viewI=false;
      this.viewS=false;
      this.viewD=false;
    }


  }
  profile1(a:string){
    if(a.includes('Profile')){
    this.profile=true;
    this.search=false;
    this.make=false;
    this.view=false;
    this.viewI=false;
    this.viewS=false;
    this.viewD=false;
    this.viewT=false;
    }
  }
  make1(a:String){
    if(a.includes('Create')){
    this.profile=false;
    this.search=false;
    this.make=true;
    this.view=false;
    this.viewI=false;
    this.viewS=false;
    this.viewD=false;
    this.viewT=false;
    }
  }
  search1(a:String){
    if(a.includes('Search')){
    this.profile=false;
    this.search=true;
    this.make=false;
    this.view=false;
    this.viewI=false;
    this.viewS=false;
    this.viewD=false;
    this.viewT=false;
    }
  }



}
