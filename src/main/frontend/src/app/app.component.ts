import { Component, OnInit } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import { AppService } from './app.service';

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
    title = 'Demicon Techinal Task D3 Cloud';

    randomUsers: any[];
    countries: any;
    nat: string;

    constructor(
        private http: HttpClient,
        private appService: AppService
    ) { }

    ngOnInit(): void {
        this.randomUsers = [];
        this.appService.getAllRandomUsers().subscribe((value) => {
            this.populateRandomUsers(value);
        });

        this.appService.getAllCountries().subscribe((value) => {
            this.countries = value;
        });
    }

    fetchRandomUsers() {
        if(this.nat) {
            this.randomUsers = [];
            this.appService.getRandomUsersByCountry(this.nat).subscribe((value) => {
                this.populateRandomUsers(value);
            });
        }
    }

    populateRandomUsers(values: any) {
        if(values) {
            values["countries"].forEach(country => {
                this.randomUsers = this.randomUsers.concat(country["users"]);
            });
        }
    }
}
