package com.github.dimsuz.playground.routing.checkout
//
//import com.github.dimsuz.playground.routing.checkout.Checkout.CancelState
//import com.github.dimsuz.playground.routing.checkout.Checkout.State
//import java.util.*
//
//data class CartItem(val id: String)
//data class Card(val id: String)
//
//interface Checkout<
//  NoItems : State<State.NoItems>,
//  HasItems : State<State.HasItems>,
//  NoCard : State<State.NoCard>,
//  CardSelected : State<State.CardSelected>,
//  CardConfirmed : State<State.CardConfirmed>,
//  OrderPlaced : State<State.OrderPlaced>
//  > {
//  interface State<T> {
//    object NoItems
//    object HasItems
//    object NoCard
//    object CardSelected
//    object CardConfirmed
//    object OrderPlaced
//  }
//
//  sealed class SelectState {
//    data class NoItemsSelect<NoItems : State<State.NoItems>>(val state: NoItems) : SelectState()
//    data class HasItemsSelect<HasItems : State<State.HasItems>>(val state: HasItems) : SelectState()
//  }
//
//  sealed class CancelState {
//    data class NoCardCancel<NoCard : State<State.NoCard>>(val state: State<State.NoCard>) :
//      CancelState()
//
//    data class CardSelectedCancel<CardSelected : State<State.CardSelected>>(val state: CardSelected) :
//      CancelState()
//
//    data class CardConfirmedCancel<CardConfirmed : State<State.CardConfirmed>>(val state: CardConfirmed) :
//      CancelState()
//  }
//
//  fun initial(): NoItems
//  fun select(state: SelectState, cartItem: CartItem): HasItems
//  fun checkout(state: HasItems): NoCard
//  fun selectCard(state: NoCard, card: Card): CardSelected
//  fun confirm(state: CardSelected): CardConfirmed
//  fun placeOrder(state: CardConfirmed): OrderPlaced
//  fun cancel(state: CancelState): HasItems
//  fun end(state: OrderPlaced): String
//}
//
//fun fillCart(checkout: Checkout, state: State<State.NoItems>): State<State.HasItems> {
//  val firstItem = CartItem(UUID.randomUUID().toString()) // user input?
//  val nextState = checkout
//    .select(Checkout.SelectState.NoItemsSelect(state), firstItem)
//  return selectMoreItems(checkout, nextState)
//}
//
//fun selectMoreItems(checkout: Checkout, state: State<State.HasItems>): State<State.HasItems> {
//  val moreItems = "true".toBoolean() // user input?
//  return if (moreItems) {
//    val nextItem = CartItem(UUID.randomUUID().toString()) // user input?
//    val nextState = checkout.select(Checkout.SelectState.HasItemsSelect(state), nextItem)
//    selectMoreItems(checkout, nextState)
//  } else {
//    state
//  }
//}
//
//fun startCheckout(checkout: Checkout, state: State<State.HasItems>): State<State.OrderPlaced> {
//  val noCard = checkout.checkout(state)
//  val card = Card(UUID.randomUUID().toString()) // user input?
//  val cardSelected = checkout.selectCard(noCard, card)
//  val useCard = "true".toBoolean() // user input?
//  return if (useCard) {
//    val cardConfirmed = checkout.confirm(cardSelected)
//    checkout.placeOrder(cardConfirmed)
//  } else {
//    val hasItems = checkout.cancel(CancelState.CardSelectedCancel(cardSelected))
//    val hasMoreItems = selectMoreItems(checkout, hasItems)
//    startCheckout(checkout, hasMoreItems)
//  }
//}
//
//fun checkoutProgram(checkout: Checkout): String {
//  val initial = checkout.initial()
//  val hasItems = fillCart(checkout, initial)
//  val orderPlaced = startCheckout(checkout, hasItems)
//  return checkout.end(orderPlaced)
//}
//
//sealed class CheckoutState<T> : State<T> {
//  object NoItems : CheckoutState<State.NoItems>()
//  data class HasItems(val items: List<CartItem>) : CheckoutState<State.HasItems>()
//  object NoCard : CheckoutState<State.NoCard>()
//  object CardSelected : CheckoutState<State.CardSelected>()
//  object CardConfirmed : CheckoutState<State.CardConfirmed>()
//  object OrderPlaced : CheckoutState<State.OrderPlaced>()
//}
//
//class CheckoutImpl : Checkout<
//  CheckoutState.NoItems,
//  CheckoutState.HasItems,
//  CheckoutState.NoCard,
//  CheckoutState.CardSelected,
//  CheckoutState.CardConfirmed,
//  CheckoutState.OrderPlaced
//  > {
//
//  override fun initial(): CheckoutState.NoItems {
//    return CheckoutState.NoItems
//  }
//
//  override fun select(state: Checkout.SelectState, cartItem: CartItem): CheckoutState.HasItems {
//    return when (state) {
//      is Checkout.SelectState.NoItemsSelect<*> -> CheckoutState.HasItems(listOf(cartItem))
//      is Checkout.SelectState.HasItemsSelect<CheckoutState.HasItems> -> CheckoutState.HasItems(state.state)
//    }
//  }
//
//  override fun checkout(state: CheckoutState.HasItems): CheckoutState.NoCard {
//    TODO("not implemented")
//  }
//
//  override fun selectCard(state: CheckoutState.NoCard, card: Card): CheckoutState.CardSelected {
//    TODO("not implemented")
//  }
//
//  override fun confirm(state: CheckoutState.CardSelected): CheckoutState.CardConfirmed {
//    TODO("not implemented")
//  }
//
//  override fun placeOrder(state: CheckoutState.CardConfirmed): CheckoutState.OrderPlaced {
//    TODO("not implemented")
//  }
//
//  override fun cancel(state: CancelState): CheckoutState.HasItems {
//    TODO("not implemented")
//  }
//
//  override fun end(state: CheckoutState.OrderPlaced): String {
//    TODO("not implemented")
//  }
//
//
//}
