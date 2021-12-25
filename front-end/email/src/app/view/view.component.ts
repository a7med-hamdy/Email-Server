import { SelectionModel } from '@angular/cdk/collections';
import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
@Component({
  selector: 'app-view',
  templateUrl: './view.component.html',
  styleUrls: ['./view.component.css']
})
export class ViewComponent implements OnInit {
  array = [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20]
  displayedColumns: string[] = [ ' ','Id'];
  dataSource: MatTableDataSource<number> = new MatTableDataSource(this.array);
  selection = new SelectionModel<number>(true, []);
  @ViewChild('paginator') paginator!: MatPaginator;
  ngAfterViewInit() {
      this.dataSource = new MatTableDataSource(this.array);
      this.dataSource.paginator = this.paginator;
  }
  constructor() { }
  requestDataSource(src:any){
    this.dataSource = new MatTableDataSource(src);
  }
  ngOnInit() {
  }


}
