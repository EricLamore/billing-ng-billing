import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAddressBilling } from '../address-billing.model';

@Component({
  selector: 'jhi-address-billing-detail',
  templateUrl: './address-billing-detail.component.html',
})
export class AddressBillingDetailComponent implements OnInit {
  address: IAddressBilling | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ address }) => {
      this.address = address;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
