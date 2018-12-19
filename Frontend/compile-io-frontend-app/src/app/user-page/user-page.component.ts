import { Component, OnInit, Input } from '@angular/core';
import { UploadService } from '../upload/upload.service';

@Component({
  selector: 'app-user-page',
  templateUrl: './user-page.component.html',
  styleUrls: ['./user-page.component.css']
})
export class UserPageComponent implements OnInit {
  @Input() username: string;
  classes: string[] = [];
  selectedClass: string = null;
  homeworks: string[] = [];
  selectedHomework: string = null;

  constructor(private uploadService: UploadService) {
    this.getClasses();
  }

  getClasses() {
    this.uploadService.getClasses().then(result => {
      result.forEach(element => {
        this.classes.push(element.toString());
      });
    }, error => {
      console.log(error);
    });
  }

  selectClass(givenClass: string) {
    if (this.selectedClass == givenClass) {
      this.selectedClass = '';
      this.homeworks = [];
    } else {
      this.selectedClass = givenClass;
      this.homeworks = [];
      this.uploadService.getHomeworks(this.selectedClass).then(result => {
      result.forEach(element => {
        this.homeworks.push(element.toString());
      });
    }, error => {
      console.log(error);
    });
    }
    
  }

  selectHomework(givenHwk: string) {
    this.selectedHomework = givenHwk;
  }

  return() {
    this.selectedHomework = null;
  }

  ngOnInit() {
  }

}
