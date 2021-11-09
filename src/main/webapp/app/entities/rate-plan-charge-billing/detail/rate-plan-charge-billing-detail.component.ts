import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRatePlanChargeBilling } from '../rate-plan-charge-billing.model';

@Component({
  selector: 'jhi-rate-plan-charge-billing-detail',
  templateUrl: './rate-plan-charge-billing-detail.component.html',
})
export class RatePlanChargeBillingDetailComponent implements OnInit {
  ratePlanCharge: IRatePlanChargeBilling | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ratePlanCharge }) => {
      this.ratePlanCharge = ratePlanCharge;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
