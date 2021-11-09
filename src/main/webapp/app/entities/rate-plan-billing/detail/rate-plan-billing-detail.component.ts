import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRatePlanBilling } from '../rate-plan-billing.model';

@Component({
  selector: 'jhi-rate-plan-billing-detail',
  templateUrl: './rate-plan-billing-detail.component.html',
})
export class RatePlanBillingDetailComponent implements OnInit {
  ratePlan: IRatePlanBilling | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ratePlan }) => {
      this.ratePlan = ratePlan;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
