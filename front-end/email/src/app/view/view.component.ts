import { RequestsService } from './../requests/requests.service';
import { DataSource, SelectionModel } from '@angular/cdk/collections';
import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
@Component({
  selector: 'app-view',
  templateUrl: './view.component.html',
  styleUrls: ['./view.component.css']
})
export class ViewComponent implements OnInit {
  displayedColumns: string[] = [' ',"ID", "subject","body","time", "priority"];
  dataSource!: MatTableDataSource<any>;
  selection = new SelectionModel<number>(true, []);
  @ViewChild('paginator') paginator!: MatPaginator;
  userID: any;

  ngAfterViewInit(str:string, str1:string) {
    this.updateDataSource(str)
  }
  public getUserID(ID:any){
    this.userID = ID;
  }
  constructor(private req:RequestsService) { }
    updateDataSource(id:string){
      (this.req.getEmails('inbox',id)).subscribe(response =>{
        this.dataSource = new MatTableDataSource<any>(response);
        console.log(response);
        this.dataSource.paginator = this.paginator;
      });
  }
  ngOnInit() {
  }


}
