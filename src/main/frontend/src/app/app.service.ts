import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable()
export class AppService {

    private getRandomUsersUrl = '/api/get-all-random-users-group-by-country';

    constructor(private http: HttpClient) { }

    getAllRandomUsers() {
        return this.http.get(`${this.getRandomUsersUrl}`);
    }

    getRandomUsersByCountry(country: string) {
        if (country) {
            return this.http.get(`${this.getRandomUsersUrl}/${country}`);
        } else {
            this.getAllRandomUsers();
        }
    }

    getAllCountries() {
        return this.http.get('/api/get-all-countries');
    }
}