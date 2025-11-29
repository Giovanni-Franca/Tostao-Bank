package Adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.TostaoBank.R
import model.Historico
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class HistoricoAdapter(private val lista: MutableList<Historico>) :
    RecyclerView.Adapter<HistoricoAdapter.HistoricoViewHolder>() {

    inner class HistoricoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtData: TextView = itemView.findViewById(R.id.txtDataTransferencia)
        val txtTipo: TextView = itemView.findViewById(R.id.txtTipoTransferencia)
        val txtDesc: TextView = itemView.findViewById(R.id.txtDescTransferencia)
        val txtValor: TextView = itemView.findViewById(R.id.txtValor)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoricoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_historico, parent, false)
        return HistoricoViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: HistoricoViewHolder, position: Int) {
        val item = lista[position]

        // formata a data
        val dataFormatada = try {
            item.dataTransferencia?.let {
                ZonedDateTime.parse(it).format(DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale("pt", "BR")))
            } ?: "—"
        } catch (e: Exception) {
            "—"
        }

        holder.txtData.text = dataFormatada
        holder.txtTipo.text = item.tipoTransferencia ?: "—"

        // verifica se a transação é pix e personaliza a descrição
        if(item.tipoTransferencia == "Pix"){
            holder.txtDesc.text = "Para: ${item.emailRecebeTransferencia ?: "Sem descrição"}"
        }
        else{
            holder.txtDesc.text = item.descTransferencia ?: ""
        }

        holder.txtValor.text = "R$ %.2f".format(item.valorTransferencia ?: 0.0)
    }

    override fun getItemCount(): Int = lista.size

    // Método útil para atualizar a lista sem recriar o adapter
    fun atualizarLista(novaLista: List<Historico>) {
        lista.clear()
        lista.addAll(novaLista)
        notifyDataSetChanged()
    }
}