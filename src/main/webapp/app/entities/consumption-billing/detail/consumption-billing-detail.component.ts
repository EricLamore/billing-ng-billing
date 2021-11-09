import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IConsumptionBilling } from '../consumption-billing.model';

@Component({
  selector: 'jhi-consumption-billing-detail',
  templateUrl: './consumption-billing-detail.component.html',
})
export class ConsumptionBillingDetailComponent implements OnInit {
  consumption: IConsumptionBilling | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ consumption }) => {
      this.consumption = consumption;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
