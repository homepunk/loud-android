package homepunk.github.com.presentation.feature.widget.parentrecycler

/**Created by Homepunk on 18.03.2019. **/
/*
class BindingParentAdapter<PARENT : BindingParentModel>(var parents: List<PARENT>): RecyclerView.Adapter<BindingParentAdapter.BindingViewHolder<PARENT>>() {
    override fun onBindViewHolder(holder: BindingViewHolder<PARENT>, position: Int) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = BindingViewHolder(inflateVH(parent, parent), variableId)


    override fun getItemCount() = parents.size

    private fun <T : ViewDataBinding> inflateVH(parent: ViewGroup, layoutId: Int) = DataBindingUtil.inflate<T>(LayoutInflater.from(parent.context), layoutId, parent, false)!!

    class BindingViewHolder<ITEM>(val binding: ViewDataBinding, val variableId: Int) : BaseRecyclerViewAdapter.BaseViewHolder<ITEM>(binding.root) {
        override fun bind(item: ITEM) {
            with(binding) {
                setVariable(variableId, item)
            }
        }
    }
}*/
