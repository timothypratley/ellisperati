(ns ellisperati.ui
  (:refer-clojure :exclude [comment list])
  (:require [goog.object :as go]
            [reagent.core :as reagent]
            [semantic-ui-react]))

(defn component [x]
  (go/get semantic-ui-react x))

(defn adapt [x]
  (reagent/adapt-react-class (component x)))

;; Run this in a REPL to generate all the adapters
(clojure.core/comment
  (require '[camel-snake-kebab.core :as csk])
  (doseq [k (sort (go/getKeys semantic-ui-react))]
    (println (clojure.core/list 'def (csk/->kebab-case (symbol k))
                                (clojure.core/list 'reagent/adapt-react-class (symbol "semantic-ui-react" k))))))

;; Paste the output here
;; ---- below this line is generated ----

(def accordion (reagent/adapt-react-class semantic-ui-react/Accordion))
(def accordion-accordion (reagent/adapt-react-class semantic-ui-react/AccordionAccordion))
(def accordion-content (reagent/adapt-react-class semantic-ui-react/AccordionContent))
(def accordion-panel (reagent/adapt-react-class semantic-ui-react/AccordionPanel))
(def accordion-title (reagent/adapt-react-class semantic-ui-react/AccordionTitle))
(def advertisement (reagent/adapt-react-class semantic-ui-react/Advertisement))
(def breadcrumb (reagent/adapt-react-class semantic-ui-react/Breadcrumb))
(def breadcrumb-divider (reagent/adapt-react-class semantic-ui-react/BreadcrumbDivider))
(def breadcrumb-section (reagent/adapt-react-class semantic-ui-react/BreadcrumbSection))
(def button (reagent/adapt-react-class semantic-ui-react/Button))
(def button-content (reagent/adapt-react-class semantic-ui-react/ButtonContent))
(def button-group (reagent/adapt-react-class semantic-ui-react/ButtonGroup))
(def button-or (reagent/adapt-react-class semantic-ui-react/ButtonOr))
(def card (reagent/adapt-react-class semantic-ui-react/Card))
(def card-content (reagent/adapt-react-class semantic-ui-react/CardContent))
(def card-description (reagent/adapt-react-class semantic-ui-react/CardDescription))
(def card-group (reagent/adapt-react-class semantic-ui-react/CardGroup))
(def card-header (reagent/adapt-react-class semantic-ui-react/CardHeader))
(def card-meta (reagent/adapt-react-class semantic-ui-react/CardMeta))
(def checkbox (reagent/adapt-react-class semantic-ui-react/Checkbox))
(def comment (reagent/adapt-react-class semantic-ui-react/Comment))
(def comment-action (reagent/adapt-react-class semantic-ui-react/CommentAction))
(def comment-actions (reagent/adapt-react-class semantic-ui-react/CommentActions))
(def comment-author (reagent/adapt-react-class semantic-ui-react/CommentAuthor))
(def comment-avatar (reagent/adapt-react-class semantic-ui-react/CommentAvatar))
(def comment-content (reagent/adapt-react-class semantic-ui-react/CommentContent))
(def comment-group (reagent/adapt-react-class semantic-ui-react/CommentGroup))
(def comment-metadata (reagent/adapt-react-class semantic-ui-react/CommentMetadata))
(def comment-text (reagent/adapt-react-class semantic-ui-react/CommentText))
(def confirm (reagent/adapt-react-class semantic-ui-react/Confirm))
(def container (reagent/adapt-react-class semantic-ui-react/Container))
(def dimmer (reagent/adapt-react-class semantic-ui-react/Dimmer))
(def dimmer-dimmable (reagent/adapt-react-class semantic-ui-react/DimmerDimmable))
(def dimmer-inner (reagent/adapt-react-class semantic-ui-react/DimmerInner))
(def divider (reagent/adapt-react-class semantic-ui-react/Divider))
(def dropdown (reagent/adapt-react-class semantic-ui-react/Dropdown))
(def dropdown-divider (reagent/adapt-react-class semantic-ui-react/DropdownDivider))
(def dropdown-header (reagent/adapt-react-class semantic-ui-react/DropdownHeader))
(def dropdown-item (reagent/adapt-react-class semantic-ui-react/DropdownItem))
(def dropdown-menu (reagent/adapt-react-class semantic-ui-react/DropdownMenu))
(def dropdown-search-input (reagent/adapt-react-class semantic-ui-react/DropdownSearchInput))
(def dropdown-text (reagent/adapt-react-class semantic-ui-react/DropdownText))
(def embed (reagent/adapt-react-class semantic-ui-react/Embed))
(def feed (reagent/adapt-react-class semantic-ui-react/Feed))
(def feed-content (reagent/adapt-react-class semantic-ui-react/FeedContent))
(def feed-date (reagent/adapt-react-class semantic-ui-react/FeedDate))
(def feed-event (reagent/adapt-react-class semantic-ui-react/FeedEvent))
(def feed-extra (reagent/adapt-react-class semantic-ui-react/FeedExtra))
(def feed-label (reagent/adapt-react-class semantic-ui-react/FeedLabel))
(def feed-like (reagent/adapt-react-class semantic-ui-react/FeedLike))
(def feed-meta (reagent/adapt-react-class semantic-ui-react/FeedMeta))
(def feed-summary (reagent/adapt-react-class semantic-ui-react/FeedSummary))
(def feed-user (reagent/adapt-react-class semantic-ui-react/FeedUser))
(def flag (reagent/adapt-react-class semantic-ui-react/Flag))
(def form (reagent/adapt-react-class semantic-ui-react/Form))
(def form-button (reagent/adapt-react-class semantic-ui-react/FormButton))
(def form-checkbox (reagent/adapt-react-class semantic-ui-react/FormCheckbox))
(def form-dropdown (reagent/adapt-react-class semantic-ui-react/FormDropdown))
(def form-field (reagent/adapt-react-class semantic-ui-react/FormField))
(def form-group (reagent/adapt-react-class semantic-ui-react/FormGroup))
(def form-input (reagent/adapt-react-class semantic-ui-react/FormInput))
(def form-radio (reagent/adapt-react-class semantic-ui-react/FormRadio))
(def form-select (reagent/adapt-react-class semantic-ui-react/FormSelect))
(def form-text-area (reagent/adapt-react-class semantic-ui-react/FormTextArea))
(def grid (reagent/adapt-react-class semantic-ui-react/Grid))
(def grid-column (reagent/adapt-react-class semantic-ui-react/GridColumn))
(def grid-row (reagent/adapt-react-class semantic-ui-react/GridRow))
(def header (reagent/adapt-react-class semantic-ui-react/Header))
(def header-content (reagent/adapt-react-class semantic-ui-react/HeaderContent))
(def header-subheader (reagent/adapt-react-class semantic-ui-react/HeaderSubheader))
(def icon (reagent/adapt-react-class semantic-ui-react/Icon))
(def icon-group (reagent/adapt-react-class semantic-ui-react/IconGroup))
(def image (reagent/adapt-react-class semantic-ui-react/Image))
(def image-group (reagent/adapt-react-class semantic-ui-react/ImageGroup))
(def input (reagent/adapt-react-class semantic-ui-react/Input))
(def item (reagent/adapt-react-class semantic-ui-react/Item))
(def item-content (reagent/adapt-react-class semantic-ui-react/ItemContent))
(def item-description (reagent/adapt-react-class semantic-ui-react/ItemDescription))
(def item-extra (reagent/adapt-react-class semantic-ui-react/ItemExtra))
(def item-group (reagent/adapt-react-class semantic-ui-react/ItemGroup))
(def item-header (reagent/adapt-react-class semantic-ui-react/ItemHeader))
(def item-image (reagent/adapt-react-class semantic-ui-react/ItemImage))
(def item-meta (reagent/adapt-react-class semantic-ui-react/ItemMeta))
(def label (reagent/adapt-react-class semantic-ui-react/Label))
(def label-detail (reagent/adapt-react-class semantic-ui-react/LabelDetail))
(def label-group (reagent/adapt-react-class semantic-ui-react/LabelGroup))
(def list (reagent/adapt-react-class semantic-ui-react/List))
(def list-content (reagent/adapt-react-class semantic-ui-react/ListContent))
(def list-description (reagent/adapt-react-class semantic-ui-react/ListDescription))
(def list-header (reagent/adapt-react-class semantic-ui-react/ListHeader))
(def list-icon (reagent/adapt-react-class semantic-ui-react/ListIcon))
(def list-item (reagent/adapt-react-class semantic-ui-react/ListItem))
(def list-list (reagent/adapt-react-class semantic-ui-react/ListList))
(def loader (reagent/adapt-react-class semantic-ui-react/Loader))
(def menu (reagent/adapt-react-class semantic-ui-react/Menu))
(def menu-header (reagent/adapt-react-class semantic-ui-react/MenuHeader))
(def menu-item (reagent/adapt-react-class semantic-ui-react/MenuItem))
(def menu-menu (reagent/adapt-react-class semantic-ui-react/MenuMenu))
(def message (reagent/adapt-react-class semantic-ui-react/Message))
(def message-content (reagent/adapt-react-class semantic-ui-react/MessageContent))
(def message-header (reagent/adapt-react-class semantic-ui-react/MessageHeader))
(def message-item (reagent/adapt-react-class semantic-ui-react/MessageItem))
(def message-list (reagent/adapt-react-class semantic-ui-react/MessageList))
(def modal (reagent/adapt-react-class semantic-ui-react/Modal))
(def modal-actions (reagent/adapt-react-class semantic-ui-react/ModalActions))
(def modal-content (reagent/adapt-react-class semantic-ui-react/ModalContent))
(def modal-description (reagent/adapt-react-class semantic-ui-react/ModalDescription))
(def modal-dimmer (reagent/adapt-react-class semantic-ui-react/ModalDimmer))
(def modal-header (reagent/adapt-react-class semantic-ui-react/ModalHeader))
(def pagination (reagent/adapt-react-class semantic-ui-react/Pagination))
(def pagination-item (reagent/adapt-react-class semantic-ui-react/PaginationItem))
(def placeholder (reagent/adapt-react-class semantic-ui-react/Placeholder))
(def placeholder-header (reagent/adapt-react-class semantic-ui-react/PlaceholderHeader))
(def placeholder-image (reagent/adapt-react-class semantic-ui-react/PlaceholderImage))
(def placeholder-line (reagent/adapt-react-class semantic-ui-react/PlaceholderLine))
(def placeholder-paragraph (reagent/adapt-react-class semantic-ui-react/PlaceholderParagraph))
(def popup (reagent/adapt-react-class semantic-ui-react/Popup))
(def popup-content (reagent/adapt-react-class semantic-ui-react/PopupContent))
(def popup-header (reagent/adapt-react-class semantic-ui-react/PopupHeader))
(def portal (reagent/adapt-react-class semantic-ui-react/Portal))
(def portal-inner (reagent/adapt-react-class semantic-ui-react/PortalInner))
(def progress (reagent/adapt-react-class semantic-ui-react/Progress))
(def radio (reagent/adapt-react-class semantic-ui-react/Radio))
(def rail (reagent/adapt-react-class semantic-ui-react/Rail))
(def rating (reagent/adapt-react-class semantic-ui-react/Rating))
(def rating-icon (reagent/adapt-react-class semantic-ui-react/RatingIcon))
(def ref (reagent/adapt-react-class semantic-ui-react/Ref))
(def reveal (reagent/adapt-react-class semantic-ui-react/Reveal))
(def reveal-content (reagent/adapt-react-class semantic-ui-react/RevealContent))
(def search (reagent/adapt-react-class semantic-ui-react/Search))
(def search-category (reagent/adapt-react-class semantic-ui-react/SearchCategory))
(def search-result (reagent/adapt-react-class semantic-ui-react/SearchResult))
(def search-results (reagent/adapt-react-class semantic-ui-react/SearchResults))
(def segment (reagent/adapt-react-class semantic-ui-react/Segment))
(def segment-group (reagent/adapt-react-class semantic-ui-react/SegmentGroup))
(def segment-inline (reagent/adapt-react-class semantic-ui-react/SegmentInline))
(def select (reagent/adapt-react-class semantic-ui-react/Select))
(def sidebar (reagent/adapt-react-class semantic-ui-react/Sidebar))
(def sidebar-pushable (reagent/adapt-react-class semantic-ui-react/SidebarPushable))
(def sidebar-pusher (reagent/adapt-react-class semantic-ui-react/SidebarPusher))
(def statistic (reagent/adapt-react-class semantic-ui-react/Statistic))
(def statistic-group (reagent/adapt-react-class semantic-ui-react/StatisticGroup))
(def statistic-label (reagent/adapt-react-class semantic-ui-react/StatisticLabel))
(def statistic-value (reagent/adapt-react-class semantic-ui-react/StatisticValue))
(def step (reagent/adapt-react-class semantic-ui-react/Step))
(def step-content (reagent/adapt-react-class semantic-ui-react/StepContent))
(def step-description (reagent/adapt-react-class semantic-ui-react/StepDescription))
(def step-group (reagent/adapt-react-class semantic-ui-react/StepGroup))
(def step-title (reagent/adapt-react-class semantic-ui-react/StepTitle))
(def sticky (reagent/adapt-react-class semantic-ui-react/Sticky))
(def tab (reagent/adapt-react-class semantic-ui-react/Tab))
(def tab-pane (reagent/adapt-react-class semantic-ui-react/TabPane))
(def table (reagent/adapt-react-class semantic-ui-react/Table))
(def table-body (reagent/adapt-react-class semantic-ui-react/TableBody))
(def table-cell (reagent/adapt-react-class semantic-ui-react/TableCell))
(def table-footer (reagent/adapt-react-class semantic-ui-react/TableFooter))
(def table-header (reagent/adapt-react-class semantic-ui-react/TableHeader))
(def table-header-cell (reagent/adapt-react-class semantic-ui-react/TableHeaderCell))
(def table-row (reagent/adapt-react-class semantic-ui-react/TableRow))
(def text-area (reagent/adapt-react-class semantic-ui-react/TextArea))
(def transition (reagent/adapt-react-class semantic-ui-react/Transition))
(def transition-group (reagent/adapt-react-class semantic-ui-react/TransitionGroup))
(def transitionable-portal (reagent/adapt-react-class semantic-ui-react/TransitionablePortal))
(def visibility (reagent/adapt-react-class semantic-ui-react/Visibility))
