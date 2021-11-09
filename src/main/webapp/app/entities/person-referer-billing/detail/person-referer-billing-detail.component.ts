import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPersonRefererBilling } from '../person-referer-billing.model';

@Component({
  selector: 'jhi-person-referer-billing-detail',
  templateUrl: './person-referer-billing-detail.component.html',
})
export class PersonRefererBillingDetailComponent implements OnInit {
  personReferer: IPersonRefererBilling | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ personReferer }) => {
      this.personReferer = personReferer;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
