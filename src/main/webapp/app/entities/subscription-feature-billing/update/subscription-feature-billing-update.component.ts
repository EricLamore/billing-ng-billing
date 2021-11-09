import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ISubscriptionFeatureBilling, SubscriptionFeatureBilling } from '../subscription-feature-billing.model';
import { SubscriptionFeatureBillingService } from '../service/subscription-feature-billing.service';

@Component({
  selector: 'jhi-subscription-feature-billing-update',
  templateUrl: './subscription-feature-billing-update.component.html',
})
export class SubscriptionFeatureBillingUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [],
    lastUpdate: [],
    description: [],
    isVisible: [],
  });

  constructor(
    protected subscriptionFeatureService: SubscriptionFeatureBillingService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ subscriptionFeature }) => {
      this.updateForm(subscriptionFeature);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const subscriptionFeature = this.createFromForm();
    if (subscriptionFeature.id !== undefined) {
      this.subscribeToSaveResponse(this.subscriptionFeatureService.update(subscriptionFeature));
    } else {
      this.subscribeToSaveResponse(this.subscriptionFeatureService.create(subscriptionFeature));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISubscriptionFeatureBilling>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(subscriptionFeature: ISubscriptionFeatureBilling): void {
    this.editForm.patchValue({
      id: subscriptionFeature.id,
      name: subscriptionFeature.name,
      lastUpdate: subscriptionFeature.lastUpdate,
      description: subscriptionFeature.description,
      isVisible: subscriptionFeature.isVisible,
    });
  }

  protected createFromForm(): ISubscriptionFeatureBilling {
    return {
      ...new SubscriptionFeatureBilling(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      lastUpdate: this.editForm.get(['lastUpdate'])!.value,
      description: this.editForm.get(['description'])!.value,
      isVisible: this.editForm.get(['isVisible'])!.value,
    };
  }
}
