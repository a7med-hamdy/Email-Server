import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {

  constructor() { }
  selected?:string="0"
  selName?:string
  ngOnInit(): void {
  }

  search(){
    console.log(this.selected)
  }


}
